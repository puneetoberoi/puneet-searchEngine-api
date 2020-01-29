const express = require("express");
var mysql = require("mysql");
const path = require("path");
const hbs = require("hbs");
const bodyParser = require("body-parser");
const cookieParser = require("cookie-parser");
const app=express();
var settings = require("./mysql/settings.json");
const viewsPath = path.join(__dirname, './template/views')
const partialsPath = path.join(__dirname, './template/partials')
app.use(bodyParser.urlencoded({ extended: true }))
app.use(express.static('views'));  // set a static folder so we donthave to put in a command for every page(about, contact etc)
app.use(bodyParser.json()); //.use is to introduce middlewware
app.use(cookieParser());

app.set('view engine', 'hbs')
app.set('views', viewsPath)
hbs.registerPartials(partialsPath)

var connection = mysql.createConnection({  //connection variable set which uses the module settings set for the db credentials.
    host: settings.url,
    user: settings.user,
    password: settings.pass,
    database: settings.db,
    multipleStatements: settings.multipleStatements
});

app.get("/", function(req, res) {
    res.render('home');
    
})


app.post('/', function (req, res) {
    console.log('=========================================================');
    console.log(req.body.URL)
    // connection.connect(function (err) {
    //   if (err) throw err;
    //   console.log("connected");
      var sql = "INSERT INTO url (url) VALUES ('"+req.body.URL+"')";
      console.log(sql);
      connection.query(sql, function (err, result) {
        if (err) throw err;
        return console.log("record entered");
       
      //})

    });
  
    res.render('home');
  })


app.listen(3000, function(req, res){
    console.log('wmk');
})