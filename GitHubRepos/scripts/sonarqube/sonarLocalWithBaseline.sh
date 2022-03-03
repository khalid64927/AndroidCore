#!/usr/bin/env bash
# fail if any commands fails
source ./scripts/sonarqube/config

if test -z "$login"
then
   echo "Error: Login token not set in config file"
   exit 1
else
	echo "Login token is $login"

	set -ex

	git fetch origin develop

	if [ -d "tmpdev" ]
	then
		echo "Dir tmpdev exist. Pulling latest dev"
		cd tmpdev
		# Pull develop to set baseline
		git checkout develop
		git pull
		cd ..
	else
		echo "Dir tmpdev not exist. Cloning dev"
		# Clone develop to set baseline
		git clone --branch develop git@bitbucket.org:ntuclink/platform-picker-app.git tmpdev
	fi

	# Overwrite sonar properties with local version
	cp scripts/sonarqube/sonar-project.properties tmpdev/sonar-project.properties
	cp local.properties tmpdev/local.properties

	cd tmpdev
	# ./gradlew compileThorUATDebugSources
	./gradlew jacocoFullReport

	cd ..

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

	export VERSION=$(git log -1 origin/develop --pretty=%h)
	make -C ns init DEFAULT_DIR=$PWD/tmpdev
	make -C ns scan ARGS="--projectVersion=$VERSION --login=$login --url=http://localhost:9000 --debug"

	#Get last analysis's uuid
	analysisUUID=$(curl -u $login: -X GET -F 'project=ntuclink_platform-picker-app' -F 'ps=1' http://localhost:9000/api/project_analyses/search | python -c "import sys, json; print(json.load(sys.stdin)['analyses'][0]['key'])")
	#Set last analysis as baseline
	curl -u $login: -X POST -F 'type=SPECIFIC_ANALYSIS' -F 'project=ntuclink_platform-picker-app' -F 'branch=master' -F "value=$analysisUUID" http://localhost:9000/api/new_code_periods/set

	./gradlew runLocalSonar
fi