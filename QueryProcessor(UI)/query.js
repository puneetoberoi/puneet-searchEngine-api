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
const url = 'mongodb://localhost:27017/QueryProcessor';
const connectionURL = 'mongodb://localhost:27017/';
const databaseName = 'QueryProcessor';
var db
var put
var methods = [];
MongoClient.connect('mongodb://localhost:27017/QueryProcessor', (err, database) => {
  if (err) return console.log(err)
  db = database.db('QueryProcessor')
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


app.get('/test/:query', function (req, res, err) {
  notes = []
  dataToSend = []
  db.collection('new').find({ $text: { $search: req.params.query } },
    { score: { $meta: "textScore" } })
    .sort({ score: { $meta: "textScore" } })
    .limit(6).toArray((error, data) => {
      //console.log(data)
      //console.log("Here is data")

      notes.push(data)
      //notes.forEach(element => console.log(element));
      //let result = data.map(a => a.score)

      let result = data.map(x => {
        if (x.score>1) {
          return x.url;
        } else {
          return [];
        }
      }).join(' ')

      dataToSend = result.split(' ')
      var one  = new Set((dataToSend))
      //    let result = data.map(function(note) {
      //     if(note.score>1){
      //       return note.score
      //     }
      // })

      // let result = data.map((note) => {
      //   if (note.score > 1) {
      //     return note
      //   }
      // })

  



      //  dataToSend = notes.forEach((note) =>{
      //    console.log(typeof(JSON.stringify(note)))
      //    console.log(JSON.stringify(note.body))
      //  })
      console.log(data)
      console.log((dataToSend))
      console.log('***************************************')
      console.log(result)
      console.log('*****************************')
      console.log(one)
      
      res.render('test', {
        items: dataToSend,
        title: 'Jaspreet Singh',
        desc: JSON.stringify(one)
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