module.exports = ({ }) => {
    const execSync = require('child_process').execSync
    execSync(`npm install nodemailer`) // Install nodemailer
    const nodemailer = require('nodemailer')
    const transporter = nodemailer.createTransport({
        host: "smtp.live.com", // Host for hotmail
        port: 587,
        secureConnection: false,
        auth: {
            user: `${process.env.MAIL_USERNAME}`,
            pass: `${process.env.MAIL_PASSWORD}`
        },
        tls: {
            ciphers: 'SSLv3'
        }
    });
    const report = require('fs').readFileSync('../GitHubRepos/app/build/dependencyUpdates/report.html', 'utf8')

    const mailOptions = {
        from: {
            name: 'GitHubRepos',
            address: process.env.MAIL_USERNAME
        },
        to: 'khalid64927@gmail.com', // Use your main account to get the email
        subject: 'Dependency update report of GitHubRepos ¯\\_(ツ)_/¯',
        text: `${report}`,
        attachments : [
        {   // utf-8 string as an attachment
                filename: 'report.html',
                path: '../GitHubRepos/app/build/dependencyUpdates/report.html'
            }
        ]
    };

    transporter.sendMail(mailOptions, (error, info) => {
        if (error) {
            console.log(error)
        }
    });
}