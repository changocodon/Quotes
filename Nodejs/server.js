const express = require('express');
const bodyParser = require('body-parser');
const app = express();
const MongoClient = require('mongodb').MongoClient;
const port = 8080;
var db;

app.set('view engine', 'ejs')
app.use(express.static('public'));
app.use(bodyParser.urlencoded({ extended: true }));
//recieved data from UI by put method
app.use(bodyParser.json());

// set data connection string and server listening
MongoClient.connect('mongodb://sang:1902@ds123930.mlab.com:23930/star-war-quotes', (err, database) => {
    if (err) return console.log(err)
    db = database;
    app.listen(port, () => {
        console.log('listening on 3000');
    });
});

//Save data from UI
app.post('/', (req, res) => {
    db.collection('quotes').save(req.body, (err, result) => {
        if (err) return console.log(err)
        console.log('saved to database');
        res.redirect('/');
    });
});

//Show list data in mongodb
app.get('/', (req, res) => {
    console.log('sang');
    db.collection('quotes').find().toArray((err, result) => {
        if (err) return console.log(err)
        //console.log('truung',result);
        // renders index.ejs
        res.render('index.ejs', { quotes: result })
    });
    console.log('end=sang');
});

//UPDATE data from UI
app.put('/up', (req, res) => {
    //console.log(req.body._id);
    db.collection('quotes').findOneAndUpdate({ _id: req.body._id },
        {
            $set: {
                name: req.body.name,
                quote: req.body.quote
            }
        }, { 
            new: true
        }).then(function(result, err){
            console.log('trung',result);
        });
        
});