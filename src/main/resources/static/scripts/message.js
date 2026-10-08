
console.log('In message.js');



class MessageModel {
	constructor() {
	}
	

	getMessageData(callback) {

		console.log('In GetMessage()');
		var xhttp = new XMLHttpRequest();
		xhttp.onreadystatechange = function() {
			if (this.readyState == 4 && this.status == 200) {
				console.log(this.responseText);
				
				this.messages = JSON.parse(this.responseText);
					
                callback(this.messages);
			 
			}
		};
		xhttp.open("GET", "http://localhost:8888/api/message", true);
		xhttp.setRequestHeader("Content-type", "application/json");
		xhttp.send();
	}


	addMessage(messageid, authorid, message, callback, callbackerror){

		console.log('In addMessage()');
		console.log(messageid);

		var xhttp = new XMLHttpRequest();
		xhttp.onreadystatechange = function() {
			if (this.readyState == 4 && this.status == 200) {
				console.log(this.responseText);
				let data = {response:this.responseText};
                callback(data);
			}

			if (this.readyState == 4 && this.status == 400) {
				console.log(this.responseText);
                let response =JSON.parse(this.responseText);
				let data = {errors: response.errormessage};
                callbackerror(data);
			}
		};

		let url;
		if (messageid)
			url = `http://localhost:8888/api/message/${messageid}`;
		else
			url = `http://localhost:8888/api/message`;

		xhttp.open("POST", url, true);
		xhttp.setRequestHeader("Content-type", "application/json");
		xhttp.send(JSON.stringify({authorid: authorid, message: message}));


	}


}

class MessageView {
	constructor() {
	}
	
	createView(messageData) {
		

		this.messageData = messageData.data;
		
        this.createCards();

	}

	createCards() {

	
        
        console.log('Ready to Create Cards');
		

        let cardlist = "";

		//Create Message Cards
		for(var message of this.messageData) {
		
            cardlist += ``
                + `<div id="card${message.messageid}" class="card">`
                + `    <div class="cardline">`
                + `        <div class="cardlineLabel">Author: </div>`
                + `        <div class="cardlineData">${message.authorname}</div>`
                + `    </div>`
                + `    <div class="cardline">`
                + `        <div class="cardlineLabel">Message: </div>`
                + `        <div class="cardlineData">${message.message}</div>`
                + `    </div>`
                + `</div>`;

		}

        let root = document.querySelector('#root')
        root.innerHTML = cardlist;

		for(var message of this.messageData) {
            let card = document.querySelector(`#card${message.messageid}`);
            card.addEventListener("click", app.handleCardClick.bind(app, message.messageid));  
            
            console.log(card);
        }


	}

	createMessageModal(messageid){

        //Get the Message
        let message = this.messageData.find(x=>x.messageid === messageid);

        //Update the header
        let modalHeader = document.querySelector('#modalheader')
		modalHeader.textContent = message.authorname;

        //Update the body
        let modalBody = document.querySelector('#modalbody')
        modalBody.innerHTML = `Message: ${message.message}`;

        //Update the footer
        let modalFooter = document.querySelector('#modalFooter');
        modalFooter.innerHTML = 
                    `<button id="btnModalFooterClose" class="modelFooterButton">Close</button>`
                +   `<button id="btnModalFooterEdit" class="modelFooterButton">Edit</button>`;

        let btnModalFooterClose = document.querySelector('#btnModalFooterClose');
        let btnModalFooterEdit = document.querySelector('#btnModalFooterEdit');

        btnModalFooterClose.addEventListener("click", ()=>app.closeModal());
        btnModalFooterEdit.addEventListener("click", ()=>app.handleOpenEditCard(message.messageid));
        
        this.showModal();
            
	}

	createAddMessageModal(){


      //Update the header
      let modalHeader = document.querySelector('#modalheader')

      modalHeader.textContent = "Add Message"

      //Update the body
      let modalBody = document.querySelector('#modalbody')
      modalBody.innerHTML = 
              `<div class="formrow"><div class="data"><div class="datalabel">Author Id:</div><div class="datainput"><input type="text" id="authorid" value=""></input></div></div></div>`
          +   `<div class="formrow"><div class="data"><div class="datalabel">Message:</div><div class="datainput"><input type="text" id="message" value=""></input></div></div></div>`;

      //Update the footer
      let modalFooter = document.querySelector('#modalFooter');
      modalFooter.innerHTML = 
                  `<button id="btnModalFooterSave" class="modelFooterButton" >Save</button>`
              +   `<button id="btnModalFooterClose" class="modelFooterButton" >Cancel</button>`;

        let btnModalFooterSave = document.querySelector('#btnModalFooterSave');
        let btnModalFooterClose = document.querySelector('#btnModalFooterClose');

        btnModalFooterSave.addEventListener("click", ()=>app.handleSaveMessageClick());
        btnModalFooterClose.addEventListener("click", ()=>app.closeModal());
      

      this.showModal();




	}

	createEditMessageModal(messageid){

        //Get the Message
        let message = this.messageData.find(x=>x.messageid === messageid);

        //Update the header
        let modalHeader = document.querySelector('#modalheader')

		modalHeader.textContent = "Edit Message"

        //Update the body
        let modalBody = document.querySelector('#modalbody')

        modalBody.innerHTML = 
                `<div class="formrow"><div class="data"><div class="datalabel">Author Id:</div><div class="datainput"><input type="text" id="authorid" value="${message.authorid}"></input></div></div></div>`
            +   `<div class="formrow"><div class="data"><div class="datalabel">Message:</div><div class="datainput"><input type="text" id="message" value="${message.message}"></input></div></div></div>`;

        //Update the footer
        let modalFooter = document.querySelector('#modalFooter');
        modalFooter.innerHTML = 
                    `<button id="btnModalFooterSave" class="modelFooterButton">Save</button>`
                +   `<button id="btnModalFooterClose" class="modelFooterButton">Cancel</button>`;

        let btnModalFooterSave = document.querySelector('#btnModalFooterSave');
        let btnModalFooterClose = document.querySelector('#btnModalFooterClose');

        btnModalFooterSave.addEventListener("click", ()=>app.handleSaveMessageClick(message.messageid));
        btnModalFooterClose.addEventListener("click", ()=>app.closeModal());

        this.showModal();


		
	}


    hideModal() {
        //Hide the Modal
        let modal = document.querySelector('#pageModal');
        modal.style.display = "none";
    }

    showModal() {
        //Hide the Modal
        let modal = document.querySelector('#pageModal');
        modal.style.display = "block";
    }


    createAlert(data) {

		data.errors.forEach((item) => {
			let alerts = document.querySelector('#alerts');
			let alert = viewHelper.createElement('div', ['alert','alert-warning','alert-dismissible','fade','show']);
			alert.setAttribute('role', 'alert');
			alert.innerHTML = item;
	
			let btnClose = viewHelper.createElement('button', ['close']);
			btnClose.setAttribute('type', 'button');
			btnClose.setAttribute('data-dismiss', 'alert');
			btnClose.setAttribute('aria-label', 'Close');
			btnClose.innerHTML = '<span aria-hidden="true">&times;</span>';
	
			alert.append(btnClose);
			alerts.append(alert);
	
		});
    }


	createAlert(data) {
        let alerts = document.querySelector('#alerts');
        let alertlist = '';
		data.errors.forEach((item) => {
            alertlist += `<div class="alert">${item}</div>`;
		});

        alerts.innerHTML = alertlist;
	}



}

class MessageController {
	constructor(model, view) {
		this.model = model;
		this.view = view;

		const element = document.querySelector('#root');

		element.addEventListener('ShowAlert', function(event) {
			app.handleShowAlert(event.detail);
		});

        const btnAddCard = document.querySelector("#btnAddCard");
        btnAddCard.addEventListener("click", function() {
            app.handleAddCardClick();
        });



        model.getMessageData(data => {
            view.createView(data);
        });

	}
	
	handleMessageData(message){
		console.log('create view');
		this.view.createView(message);
	}
	

	handleCardClick(id) {
		app.view.createMessageModal(id);
    }

	handleAddCardClick(id) {
		console.log('modal - Add New - clicked');
		this.view.createAddMessageModal(id);
	}

	handleSaveMessageClick(id) {
		console.log('Message Save clicked');

        let authorid = document.querySelector("#authorid").value;
		let message = document.querySelector("#message").value;

        this.model.addMessage(id, authorid, message, 
        
            //Success
            ()=>{
                app.handleMessageAdded();
            }, 
            error=>{
                app.handleShowAlert(error);
            });
        

	}

	handleMessageAdded() {

        this.model.getMessageData(data => {
            this.view.createView(data);
        });

        this.view.hideModal();

	}

	handleShowAlert(data) {
		this.view.createAlert(data);
	}



	handleOpenEditCard(id) {
		
		console.log('modal - Edit ' + id + ' - clicked');
		this.view.createEditMessageModal(id);	
	}

    closeModal() {
        this.view.hideModal();
    }



}


const app = new MessageController(new MessageModel(), new MessageView());
