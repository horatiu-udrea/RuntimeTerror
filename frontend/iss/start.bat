ECHO OFF
echo ##builing app
call npm build

echo ### starting server on port 5500
call live-server --port=5500 --open=dist
