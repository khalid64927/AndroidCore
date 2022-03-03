#!/usr/bin/env bash
# fail if any commands fails
source ./scripts/sonarqube/config

if test -z "$login"
then
   echo "Error: Login token not set in config file"
   exit 1
else
	rm -r -f tmpprj

	# Run unit tests and generate jacoco report
	./gradlew clean jacocoFullReport
	set -ex


	# Copy current working files to scan
	rsync -a . tmpprj --exclude tmpprj

	# Overwrite sonar properties with local version
	cp scripts/sonarqube/sonar-project.properties tmpprj/sonar-project.properties

	if [ -d "ns" ]
	then
		cd ns
		git fetch origin master
		git checkout master
		git pull
		cd ..
	else
		# Clone Sonar Scanner Repository and do scanning
		git clone --branch master git@bitbucket.org:ntuclink/ne-sonar-scanner.git ns
	fi

	export VERSION=$(git log -1 --pretty=%h)
	make -C ns init DEFAULT_DIR=$PWD/tmpprj
	make -C ns scan ARGS="--projectVersion=$VERSION --login=$login --url=http://localhost:9000 --debug"

	rm -r -f tmpprj
fi

