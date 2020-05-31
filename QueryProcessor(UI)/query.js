const express = require("express");
var mysql = require("mysql");
const path = require("path");
const hbs = require("hbs");
const bodyParser = require("body-parser");
const cookieParser = require("cookie-parser");
const app = express();
var MongoClient = require('mongodb').MongoClient;
var assert = require('assert');
var settings = require("./mysql/settings.json");
const viewsPath = path.join(__dirname, './template/views')
const partialsPath = path.join(__dirname, './template/partials');
var urlencodedParser = bodyParser.urlencoded({ extended: true })
app.use(express.static('views'));  // set a static folder so we donthave to put in a command for every page(about, contact etc)
app.use(bodyParser.json()); //.use is to introduce middlewware
app.use(cookieParser());

app.set('view engine', 'hbs')
app.set('views', viewsPath)
hbs.registerPartials(partialsPath)
//let response;
//const url = 'mongodb://localhost:27017/QueryProcessor';
const atlastURL="mongodb+srv://puneet:02040204@nodeapi-etlso.mongodb.net/test?retryWrites=true&w=majority";
//const connectionURL = 'mongodb://localhost:27017/';
const databaseName = 'javamongo';
var db
var put
var methods = [];
MongoClient.connect('mongodb+srv://puneet:02040204@nodeapi-etlso.mongodb.net/test?retryWrites=true&w=majority', (err, database) => {
  if (err) return console.log(err)
  db = database.db(databaseName)
  console.log("connected");
  app.listen(process.env.PORT || 3000, () => {
    console.log('listening on 3000')
  })
})

// function getApart(req, res) {
//   try {  // try and catch for if there is an error with this code segment it will not disrupt the whole program and should not let it freeze.

//     console.log(req)
//     db.collection.find({body:req.params.query}).toArray(function (error, result) {
//           console.log(result);

//           this.originalRes.render("test", {items : result});   //consulting apartments.ejs

//       }.bind({ originalReq: req, originalRes: res }));



//   } catch (ex) {
//       res.send('Internal error');
//   }
// }


app.get('/', function (req, res, next) {
  res.render("home", { title: 'Jaspreet Singh' });
})


app.get('/test/:query', function (req, res) {
  urls=[]
  notes = []
  dataToSend = []
  db.collection('mongo').find({ $text: { $search: req.params.query } },
    { score: { $meta: "textScore" } })
    .sort({ score: { $meta: "textScore" } })
    .limit(6).toArray((error, data) => {
      if(error) throw error;
      notes.push(data)
      
      let result = data.map(a =>{ 
        if(a.score>=0.5){
          return a.url;
        }else return []
      })
      var one = new Set()
      result.forEach(element => one.add(element));
      one.forEach(element=>urls.push(element))
      
      res.render('test', {
        items: urls,
        title: 'Jaspreet Singh',
        //desc: JSON.stringify(data)
      })

      // res.render('test', {items:arr,
      //                     title: 'Jaspreet Singh'})
    })

})


app.post('/test/', urlencodedParser, function (req, res, next) {
  var put = req.body.query;
  //var s =(req.body.query)
  //console.log(put)
  //var s = put.split(" ")
  //console.log(typeof(put))
  db.collection("query").insert({ body: put }, function (err, res) {
    if (err) throw err;
    //console.log("1 document inserted");
  });
  res.redirect('/test/' + put)
})