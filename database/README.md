### Database
Used [Datagrip](https://www.jetbrains.com/datagrip/?gclid=EAIaIQobChMIufqJxb6x6QIVBoGyCh0w3gi7EAAYASAAEgKsN_D_BwE), [ MySql](https://www.mysql.com/) and [Xampp](https://www.apachefriends.org/ro/download.html) package that incorporates `Apache HTTP Server` and `MySql`.

XAMPP installation:
- Under Server check `Apache` and `MySql`

How to create database:
- Open `XAMPP Control Panel` which is located where you installed XAMPP (Usually `C:\Program Files\xampp`)
- Press the `Start` button of Apache and MySql 
- Press the `Admin` button of MySql (this will open "phpMyAdmin" in your default internet browser )
- Click the `New` button on the left to create a new database
- Input the name `CMS` and then press `Create` (the CMS database is created with username: `"root"` and password: `""` by default)

How to configure Datagrip with our database (in order to run sql scripts):
- Assure that Apache and MySql from XAMPP are up and running
- Open Datagrip
- On the left press the + button (that will open a dropdown)
- Select DataSource and in the new dropdown look for MySql and select it (a new window opens)
- Type `root` in the `User` property (the username of our database)
- Type `CMS` in the `Database` property (the name of our database)
- Press the `Test Connection` button and look for a green tick

How to run sql scripts:
- Assure that Apache and MySql from XAMPP are up and running
- In Datagrip do File->Open and go to `/database`
- Click on `ddl.sql`
- On the right click on `session` (a dropdown will appear)
- Click on `CMS@localhost` and the click on `New Session`
- Press Ctrl+A to select everything in the file
- Click on the Run green arrow on the left

