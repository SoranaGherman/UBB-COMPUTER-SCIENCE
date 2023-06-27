class EventHandling {
    constructor(){

        this.templatesIds = "";
        const thisObject = this;

        document.getElementById("add_keyword_button").addEventListener("click", this.AddKeywordButton);
        document.getElementById("filter_documents_button").addEventListener("click", function(){
                thisObject.loadDocuments(thisObject);
        });

        document.getElementById("show_button").addEventListener("click", function(){
            thisObject.loadTemplates(thisObject);
        });

        document.getElementById("logout_button").addEventListener("click", function(){
            thisObject.logout();
        });


        this.loadKeywords();
    }


    loadTemplates(thisObject){
        const getRequest = new XMLHttpRequest();
    
        if (thisObject.templatesIds == "") {
            window.alert("Please select a document!");
            return;
        }

        getRequest.open("GET", "../../server/controller/controller.php?func=getTemplates&ids=" + thisObject.templatesIds);
        getRequest.send();   
        
        getRequest.onload = function() {

            const resultArray = JSON.parse(this.responseText);

            const table = document.getElementById('cols3');
            
            // Some cleanup
            while (table.firstChild) {
                table.removeChild(table.firstChild);
            }
            
            resultArray.forEach(element => {
                // create the row
                const newRow = document.createElement('tr');
                newRow.id = element.id;

                // create the category cell and add it to the row
                const categoryCell = document.createElement('td');
                categoryCell.textContent = element.name;
                newRow.appendChild(categoryCell);

                // create the type cell and add it to the row
                const typeCell = document.createElement('td');
                typeCell.textContent = element.textContent;
                newRow.appendChild(typeCell);

                table.appendChild(newRow);

            });
        }

    }


    AddKeywordButton(event) {
        const key = document.getElementById("keyword_key").value;
        const value = document.getElementById("keyword_value").value;

        if(key == "" || value == ""){
            window.alert("Please fill in all fields!");
            return;
        }

        const postRequestBody = "func=add&key=" + key + "&value=" + value;
        const postRequest = new XMLHttpRequest();
    
        postRequest.open("POST", "../../server/controller/controller.php");
        postRequest.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
        postRequest.send(postRequestBody);   
        
        postRequest.onload = function() {
            const response = this.responseText;
            if (response == 401) {
                window.alert("You are not logged in!");
                return;
            }

            window.alert("Room was successfully inserted! :)");
            this.loadKeywords();
        }
    }

    loadKeywords() {

        const getRequest = new XMLHttpRequest();
    
        getRequest.open("GET", "../../server/controller/controller.php?func=getKeyword");
        getRequest.send();   
        
        getRequest.onload = function() {

            if (this.responseText == 401) {
                window.alert("You are not logged in!");
                return;
            }

            const resultArray = JSON.parse(this.responseText);
            // resultArray.forEach(function(current, index, array) {
            //     array[index] = JSON.parse(current);
            // });

            const table = document.getElementById('cols');
            
            // Some cleanup
            while (table.firstChild) {
                table.removeChild(table.firstChild);
            }

            resultArray.forEach(element => {
                // create the row
                const newRow = document.createElement('tr');
                newRow.id = element.id;

                // create the category cell and add it to the row
                const categoryCell = document.createElement('td');
                categoryCell.textContent = element.key;
                newRow.appendChild(categoryCell);

                // create the type cell and add it to the row
                const typeCell = document.createElement('td');
                typeCell.textContent = element.value;
                newRow.appendChild(typeCell);

                table.appendChild(newRow);

            });
        }
    }

    changeSelectedId(templates){
        this.templatesIds = templates;
        console.log(templates);
    }

    loadDocuments(thisObject) {

        const getRequest = new XMLHttpRequest();

        const title = document.getElementById("title").value;
        
        getRequest.open("GET", "http://localhost:82/exam22e/server/controller/controller.php?func=getDocuments&title=" + title);
        getRequest.send();   
        
        getRequest.onload = function() {

            if (this.responseText == 401) {
                window.alert("You are not logged in!");
                return;
            }

            const resultArray = JSON.parse(this.responseText);
            // resultArray.forEach(function(current, index, array) {
            //     array[index] = JSON.parse(current);
            // });

            const table = document.getElementById('cols2');
            
            //  Some cleanup
            while (table.firstChild) {
                table.removeChild(table.firstChild);
            }
            
            resultArray.forEach(element => {
                // create the row
                const newRow = document.createElement('tr');
                newRow.id = element.id;

                // create the category cell and add it to the row
                const categoryCell = document.createElement('td');
                categoryCell.textContent = element.title;
                newRow.appendChild(categoryCell);

                // create the type cell and add it to the row
                const typeCell = document.createElement('td');
                typeCell.textContent = element.templates;
                newRow.appendChild(typeCell);

                newRow.onclick = function() {
                    
                    thisObject.changeSelectedId(element.templates);
                    
                    // cleanup
                    var rows = document.querySelectorAll("#cols2 tr");
                    console.log(rows);
                    rows.forEach(function(row) {
                        row.addEventListener("click", function() {
                            // Remove the "selected" class from any previously selected rows
                            document.querySelectorAll("#cols2 tr.selected").forEach(function(selectedRow) {
                                selectedRow.classList.remove("selected");
                            });

                            // Add the "selected" class to the clicked row
                            row.classList.add("selected");
                        });
                    });
                };

                table.appendChild(newRow);

            });
        }
    }

    logout(){
        const getRequest = new XMLHttpRequest();
    
        getRequest.open("GET", "../../server/controller/controller.php?func=logout");
        getRequest.send();   
        
        getRequest.onload = function() {

            const response = this.responseText;

            if (response == 1 || response == 401) {
                window.location.href = "../pages/login.html";
                localStorage.removeItem("user");
                return;
            }

            alert("Something went wrong!");
        }
    }
}
   
