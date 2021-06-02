// The Cloud Functions for Firebase SDK to create Cloud Functions and setup triggers.
const functions = require('firebase-functions');

// The Firebase Admin SDK to access Firestore.
const admin = require('firebase-admin');
admin.initializeApp();

const nodemailer = require("nodemailer");

exports.addMessage = functions.https.onRequest((req, res)=>{ //get방식 통해서 메세지 넣기
  const original = req.query.text;

  admin.database().ref('/messages').push({original:original}).then(snapshot =>{
    
    res.redirect(303, snapshot.ref);
  });
});

//Listens for new messages added to /messages/:pushId/original and creates an
//uppercase version of the message to /messages/:pushId/uppercase
exports.makeUppercase = functions.database.ref('/messages/{pushId}/original') //들어온 메세지 
    .onCreate((snapshot, context) => {
      // Grab the current value of what was written to the Realtime Database.
      const original = snapshot.val();
      functions.logger.log('Uppercasing', context.params.pushId, original);
      const uppercase = original.toUpperCase();
      // You must return a Promise when performing asynchronous tasks inside a Functions such as
      // writing to the Firebase Realtime Database.
      // Setting an "uppercase" sibling in the Realtime Database returns a Promise.
      return snapshot.ref.parent.child('uppercase').set(uppercase);
    });


exports.sendWelcomeEmail = functions.auth.user().onCreate((user) => {
  
    // Generate test SMTP service account from ethereal.email
    // Only needed if you don't have a real mail account for testing
    

    // create reusable transporter object using the default SMTP transport
    let transporter = nodemailer.createTransport({
      service: 'Gmail',
      auth: {
        user: 'wsdf25867@gmail.com', // generated ethereal user
        pass: 'idwypkuhhzjadjei', // generated ethereal password
      },
    });
  
    // send mail with defined transport object
    let info = transporter.sendMail({
      from: '"Ryan 👻" <wsdf25867@gmail.com>', // sender address
      to: user.email, // list of receivers
      subject: "환영합니다", // Subject line
      text: "회원가입완료 되었습니다."+"\n"+"http://222.108.12.176:8888/"+user.uid, // plain text body
      //html: "<b>Hello world?</b>", // html body
    });

     
  
    console.log("Message sent: %s", info.messageId);
    // Message sent: <b658f8ca-6296-ccf4-8306-87d57a0b4321@example.com>
  
    // Preview only available when sending through an Ethereal account
    console.log("Preview URL: %s", nodemailer.getTestMessageUrl(info));
    // Preview URL: https://ethereal.email/message/WaQKMgKddxQDoou...

    
});


exports.sendByeEmail = functions.auth.user().onDelete((user) => {
  
  
    // create reusable transporter object using the default SMTP transport
  let transporter = nodemailer.createTransport({
      service: 'Gmail',
      auth: {
        user: 'wsdf25867@gmail.com', // generated ethereal user
        pass: 'idwypkuhhzjadjei', // generated ethereal password
      },
    });
  
    // send mail with defined transport object
  let info = transporter.sendMail({
      from: '"Ryan 👻" <wsdf25867@gmail.com>', // sender address
      to: user.email, // list of receivers
      subject: "탈퇴", // Subject line
      text: user.email +"탈퇴완료", // plain text body
      //html: "<b>Hello world?</b>", // html body
    });
  
    console.log("Message sent: %s", info.messageId);
    // Message sent: <b658f8ca-6296-ccf4-8306-87d57a0b4321@example.com>
  
    // Preview only available when sending through an Ethereal account
    console.log("Preview URL: %s", nodemailer.getTestMessageUrl(info));
    // Preview URL: https://ethereal.email/message/WaQKMgKddxQDoou...
  
});