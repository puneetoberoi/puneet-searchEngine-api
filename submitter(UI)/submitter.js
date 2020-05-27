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
app.use(express.static('views')); 
app.use(bodyParser.json()); // introducing middlewware
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

app.post('/', (req, res)=>{
  let url = req.body.URL
  if(url.match('(http:\/\/ | https:\/\/)?(www)?\.[a-zA-Z0-9]*(([.][a-zA-Z]*)+)+')){
    var sql = "select url from url where url = '"+url+"'";
    console.log(sql)
    connection.query(sql, (err, row)=>{
       if(!row[0]){
        //res.json('valid input')
        var insert = "insert into url(url) values ('"+req.body.URL+"')";
        connection.query(insert, (err, data)=>{
          console.log(data)
        })
      } else if(row[0].url == url){
        res.json({url: "Duplicate"})
      }
      res.render('home');
    })
  }else{
    res.json('URL not valid')
  }
  // try{
  //   var sql = "insert into url(url) values ('"+req.body.URL+"')";
  //   console.log(sql)
  //   connection.query(sql, (err, rows)=>{
  //     if(err) throw new Error("Duplicate Entry")
  //   })
  //   res.render('home');

  // }catch(err){
  //   if(err.code == 'ER_DUP_ENTRY' || err.errno == 1062){

  //     console.log(err)
  //   }
    
    // if(err.code == 'ER_DUP_ENTRY' || err.errno == 1062)
    //   {
    //       console.log('Here you can handle duplication')
    //       //throw new Error("Duplicate key")
    //   }
    //   else{
    //      console.log('Other error in the query')
    //   }
  //}

//   try{
//     console.log('=========================================================');
//     console.log(req.body.URL)

//     var sql = "INSERT INTO url (url) VALUES ('"+req.body.URL+"')";
//     console.log(sql);
//   connection.query(sql, function(err,rows){
//     if(err)
//     {

//       if(err.code == 'ER_DUP_ENTRY' || err.errno == 1062)
//       {
//           console.log('Here you can handle duplication')
//           //throw new Error("Duplicate key")
//       }
//       else{
//          console.log('Other error in the query')
//       }

//     } 
// })
// res.render('home');
//   }catch(err){
//     console.log(err)
//     res.status(400).json(err.message);

//   }
})


// app.post('/', function (req, res) {
//   try{
//     console.log('=========================================================');
//     console.log(req.body.URL)

//     var sql = "INSERT INTO url (url) VALUES ('"+req.body.URL+"')";
//     console.log(sql);
//     connection.query(sql, function(err, result) {
//       if(err) throw err
//       return console.log("record entered")
//     })

//   }catch(err){
//     if(err.errno==1062){   
//       req.flash('message','The entry already exist.'); //we send the flash msg
//       return res.redirect('/admin/userPanel');
//       db.end();
//       }
//       else{
//           throw err;
//       db.end();
//       }
//   }
    
   
//     // // connection.connect(function (err) {
//     // //   if (err) throw err;
//     // //   console.log("connected");
     
//     //   connection.query(sql, function (err, result) {
//     //     if (err) throw err;
//     //     return console.log("record entered");
       
//     //   //})

//     // });
  
//     res.render('home');
//   })


app.listen(3000, function(req, res){
    console.log('wmk');
})