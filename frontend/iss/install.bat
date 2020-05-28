ECHO OFF
echo ### installing live-server
call npm install -g live-server@1.2.1

echo ### Install app modules
call npm install

echo ### building the app
call npm run build 