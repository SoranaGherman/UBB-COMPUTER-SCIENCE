class EventHandling {
    constructor() {
        this.currentPage = 0;
        const thisObject = this; 

        this.selectedId = -1;

        document.getElementById("home-page").addEventListener("click", this.homeBtnClicked);
        document.getElementById("db-insert").addEventListener("click", this.dbInsertBtnClicked);
        document.getElementById("db-update").addEventListener("click", this.dbUpdateBtnClicked);
        document.getElementById("db-delete").addEventListener("click", this.dbDeleteBtnClicked);

        document.getElementById("prev-rooms-btn-delete").addEventListener("click", function(){
            if(thisObject.currentPage > 0){
                thisObject.currentPage--;
                thisObject.loadRooms(thisObject.currentPage, thisObject);
            }
        });
        document.getElementById("next-rooms-btn-delete").addEventListener("click", function(){
            thisObject.currentPage++;
            thisObject.loadRooms(thisObject.currentPage, thisObject);
        
        });

        document.getElementById("submit-delete-btn").addEventListener("click", function(){
            if(thisObject.selectedId <= 0){
                window.alert("First select the room to be deleted!");
                return;
            }
    
            const roomID = thisObject.selectedId;
            const deleteRequest = new XMLHttpRequest();
    
            deleteRequest.open("DELETE", "../../server/controller/controller.php?which=delR&roomId=" + roomID);
            
            deleteRequest.send();
    
            window.alert("Room was successfully deleted! :)");
            thisObject.loadRooms(thisObject.currentPage, thisObject);
        });

        this.loadRooms(this.currentPage, thisObject);
    }

    homeBtnClicked(event) {
        document.location.href = "../../index.html";
    }

    dbInsertBtnClicked(event) {
        document.location.href = "./insert.html";
    }

    dbUpdateBtnClicked(event) {
        document.location.href = "./update.html";
    }

    dbDeleteBtnClicked(event) {
        document.location.href = "./delete.html";
    }

    changeSelectedId(roomId){
        this.selectedId = roomId;
    }

    loadRooms(page, thisObject) {
        const getRequest = new XMLHttpRequest();
        
        getRequest.open("GET", "../../server/controller/controller.php?which=select"  +
                        "&page=" + page);
        getRequest.send();

        let table = document.getElementById('cols');


        while(table.firstChild){
            table.removeChild(table.firstChild);
        }


        getRequest.onload = function() {
            const resultArray = JSON.parse(this.responseText);
            resultArray.forEach(function(current, index, array) {
                array[index] = JSON.parse(current);
            });

            resultArray.forEach(element => {
                // create the row
                const newRow = document.createElement('tr');
                newRow.id = element.id;

                // create the category cell and add it to the row
                const categoryCell = document.createElement('td');
                categoryCell.textContent = element.category;
                newRow.appendChild(categoryCell);

                // create the type cell and add it to the row
                const typeCell = document.createElement('td');
                typeCell.textContent = element.type;
                newRow.appendChild(typeCell);

                // create the price cell and add it to the row
                const priceCell = document.createElement('td');
                priceCell.textContent = element.price;
                newRow.appendChild(priceCell);

                // create the hotel cell and add it to the row
                const hotelCell = document.createElement('td');
                hotelCell.textContent = element.hotel;
               
                newRow.appendChild(hotelCell);

                newRow.onclick = function() {thisObject.changeSelectedId(element.id) 
                    var rows = document.querySelectorAll("#cols tr");

                    rows.forEach(function(row) {
                        row.addEventListener("click", function() {
                            // Remove the "selected" class from any previously selected rows
                            document.querySelectorAll("#cols tr.selected").forEach(function(selectedRow) {
                                selectedRow.classList.remove("selected");
                            });
                            // Add the "selected" class to the clicked row
                            row.classList.add("selected");
                        });
                    });
                };
                
                // add the new row to the table
                table.appendChild(newRow);
            });

            for(let i=resultArray.length; i<4; i++){
                const newRow = document.createElement('tr');
                table.appendChild(newRow);
            }
        }
    }

}