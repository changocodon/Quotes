const express = require('express');
const bodyParser = require('body-parser');
const app = express();
const MongoClient = require('mongodb').MongoClient;
const port = 8080;
var ObjectId = require('mongodb').ObjectID;
var db;

app.set('view engine', 'ejs')
app.use(express.static('public'));
app.use(express.static('view'));
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

    var isExsited = null;
    checkExisted(req).then((res) => {
        console.log(res);
        isExsited = res;
        console.log(isExsited);
    });
    if (isExsited == false) {
        db.collection('quotes').save(req.body, (err, result) => {
            if (err) return console.log(err)
            console.log('saved to database');
            res.redirect('/');
        });
    }

});

//Show list data in mongodb
app.get('/', (req, res) => {
    db.collection('quotes').find().toArray((err, result) => {
        if (err) return console.log(err)
        // renders index.ejs       
        res.render('index.ejs', { quotes: result })
    });
});

//UPDATE data from UI
app.put('/', (req, res) => {
    db.collection('quotes').findOneAndUpdate({ _id: ObjectId(req.body._id) },
        {
            $set: {
                name: req.body.name,
                quote: req.body.quote
            }
        }, {
            upsert: true
        }).then((result, err) => {
            console.log('sangput');
            res.send(result);
        });
});
//Detele quote choosed by user
app.delete('/', (req, res) => {
    db.collection('quotes').findOneAndDelete({ _id: ObjectId(req.body._id) },
        (err, result) => {
            if (err) return res.send(500, err)
            res.send('A quote got deleted');
        });
});


var checkExisted = (req) => {
    //check data if exsited
    return db.collection('quotes').findOne({ quote: req.body.quote })
        .then((err, res) => {
            if (err || res != null) {
                console.log(err);
                return true
            }
            return false;
        });
}
