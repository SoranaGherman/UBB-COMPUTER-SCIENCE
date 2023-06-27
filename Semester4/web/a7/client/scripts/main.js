class EventHandling {
    constructor() {
        this.currentPage = 0;
        this.currentPage2 = 0;
        this.currentPageBooked = 0;
        const thisObject = this; 

        this.selectedId = -1;
        this.selectedIdBooked = -1;

        this.category = "all";
        this.type = "all";
        this.price = 0;
        this.hotel = "all";

        this.start = "none";
        this.end = "none";

        this.bookCateg = "";
        this.bookType = "";
        this.bookPrice = "";
        this.bookHotel = "";

        thisObject.loadRooms(thisObject.currentPage, thisObject);
        thisObject.loadRoomsBooked(thisObject.currentPage2, thisObject);

        document.getElementById("home-page").addEventListener("click", this.homeBtnClicked);
        document.getElementById("db-insert").addEventListener("click", this.dbInsertBtnClicked);
        document.getElementById("db-update").addEventListener("click", this.dbUpdateBtnClicked);
        document.getElementById("db-delete").addEventListener("click", this.dbDeleteBtnClicked);
        
        document.getElementById("prev-rooms-btn").addEventListener("click", function(){
            if(thisObject.currentPage > 0){
                thisObject.currentPage--;
                thisObject.loadRooms(thisObject.currentPage, thisObject);
            }
        });

        document.getElementById("next-rooms-btn").addEventListener("click", function(){
            thisObject.currentPage++;
            thisObject.loadRooms(thisObject.currentPage, thisObject);
        });

        document.getElementById("prev-rooms-btn2").addEventListener("click", function(){
            if(thisObject.currentPage2 > 0){
                thisObject.currentPage2--;
                thisObject.loadRoomsBooked(thisObject.currentPage2, thisObject);
            }
        });

        document.getElementById("next-rooms-btn2").addEventListener("click", function(){
            thisObject.currentPage2++;
            thisObject.loadRoomsBooked(thisObject.currentPage2, thisObject);
        });

        document.getElementById("filter-button").addEventListener("click", function(){
    
            thisObject.category = document.getElementById("room-category").value;
            thisObject.type = document.getElementById("room-type").value;
            thisObject.price = document.getElementById("room-price").value;
            thisObject.hotel = document.getElementById("room-hotel").value;

            thisObject.loadRooms(thisObject.currentPage, thisObject);

        });

        document.getElementById("book-button").addEventListener("click", function(){
            if(thisObject.selectedId <= 0){
                window.alert("First select the room to be reserved!");
                return;
            }
           
            thisObject.start = document.getElementById("room-startDate").value;
            thisObject.end = document.getElementById("room-endDate").value;

            if(thisObject.start === "" || thisObject.end === ""){
                window.alert("First select the dates you want to reserve the room!");
                return;
            }
           

            const postRequest = new XMLHttpRequest();

            postRequest.open("POST", "../../server/controller/controller.php?which=b&category="+ thisObject.bookCateg + "&type=" + thisObject.bookType + "&price=" + thisObject.bookPrice + "&hotel=" + thisObject.bookHotel
                             + "&start=" + thisObject.start + "&end=" + thisObject.end);
            postRequest.send();
    
            window.alert("Room was successfully booked! :)");
            thisObject.loadRoomsBooked(thisObject.currentPage2, thisObject);


        });

        document.getElementById("cancel-button").addEventListener("click", function(){
            if(thisObject.selectedIdBooked <= 0){
                window.alert("First select the room to be canceled!");
                return;
            }
    
            const roomID = thisObject.selectedIdBooked;
            console.log(thisObject.selectedIdBooked);
            const deleteRequest = new XMLHttpRequest();
    
            deleteRequest.open("DELETE", "../../server/controller/controller.php?which=bR&roomId=" + roomID);
            
            deleteRequest.send();
    
            window.alert("Room was successfully canceled! :)");
            thisObject.loadRoomsBooked(thisObject.currentPage2, thisObject);
        });
    }

    homeBtnClicked(event) {
        document.location.href = "./index.html";
    }

    dbInsertBtnClicked(event) {
        document.location.href = "./client/pages/insert.html";
    }

    dbUpdateBtnClicked(event) {
        document.location.href = "./client/pages/update.html";
    }

    dbDeleteBtnClicked(event) {
        document.location.href = "./client/pages/delete.html";
    }

    changeSelectedId(roomId, category, type, price, hotel){
        this.selectedId = roomId;
        this.bookCateg = category;
        this.bookType = type;
        this.bookPrice = price;
        this.bookHotel = hotel;
    }

    changeSelectedId2(roomId){
        this.selectedIdBooked = roomId;
    }


   
    loadRooms(page, thisObject) {
        console.log(page);
    
        const params = new URLSearchParams({
            which: 'filter',
            category: thisObject.category,
            type: thisObject.type,
            price: thisObject.price,
            hotel: thisObject.hotel,
            page: page
        });
    
        fetch('../../server/controller/controller.php?' + params, {
            method: 'GET'
        })
        .then(response => response.json())
        .then(resultArray => {
            resultArray.forEach(function(current, index, array) {
                array[index] = JSON.parse(current);
            });
    
            let table = document.getElementById('cols');
    
            while(table.firstChild){
                table.removeChild(table.firstChild);
            }
    
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
    
                newRow.onclick = function() {
                    thisObject.changeSelectedId(element.id, element.category, element.type, element.price, element.hotel);
    
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
        })
        .catch(error => {
            console.error('Error:', error);
        });
    }

    loadRoomsBooked(page, thisObject) {
        
        const getRequest = new XMLHttpRequest();
    
        getRequest.open("GET", "../../server/controller/controller.php?which=booked&page=" + page);

        getRequest.send();

        let table = document.getElementById('cols2');


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

                // create the start cell and add it to the row
                const startCell = document.createElement('td');
                startCell.textContent = element.start;
               
                newRow.appendChild(startCell);

                // create the end cell and add it to the row
                const endCell = document.createElement('td');
                endCell.textContent = element.end;
               
                newRow.appendChild(endCell);

                // add the new row to the table
                table.appendChild(newRow);

                newRow.onclick = function() {
                    thisObject.changeSelectedId2(element.id);
    
                    var rows = document.querySelectorAll("#cols2 tr");
    
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
            });

            for(let i=resultArray.length; i<4; i++){
                const newRow = document.createElement('tr');
                table.appendChild(newRow);
            }
        }
    }
    
}