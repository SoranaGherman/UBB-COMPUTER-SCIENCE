class EventHandling {
    constructor(){

        const thisObject = this;
        document.getElementById("filter_players_button").addEventListener("click", this.filterPlayersButton);
        document.getElementById("show_degree_button").addEventListener("click", this.showDegreeButton);

    }

    filterPlayersButton(event){
        event.preventDefault(); // Prevent form submission and page refresh

        const getRequest = new XMLHttpRequest();

        const name = document.getElementById("name").value;
        getRequest.open("GET", "http://localhost:82/sport/server/controller/controller.php?func=filterPlayers&name_player=" + name);
        getRequest.send(); 
        


        getRequest.onload = function() {

            const resultArray = JSON.parse(this.responseText);
            const table = document.getElementById('cols');

            while(table.firstChild){
                table.removeChild(table.firstChild);
            }

            resultArray.sort((a, b) => {
                // Convert names to lowercase for case-insensitive sorting
                const nameA = a.name.toLowerCase();
                const nameB = b.name.toLowerCase();
        
                if (nameA < nameB) {
                    return -1; // a should be placed before b
                }
                if (nameA > nameB) {
                    return 1; // a should be placed after b
                }
                return 0; // names are equal
            });

            resultArray.forEach(element => {
                // create the row
                const newRow = document.createElement('tr');
                newRow.id = element.id;

                // create the category cell and add it to the row
                const nameCell = document.createElement('td');
                nameCell.textContent = element.name;
                newRow.appendChild(nameCell);

                 // create the category cell and add it to the row
                 const posCell = document.createElement('td');
                 posCell.textContent = element.position;
                 newRow.appendChild(posCell);

                 table.appendChild(newRow);
            });

        }
    }

    showDegreeButton(event){
        event.preventDefault(); // Prevent form submission and page refresh

        console.log("ok");

        const getRequest = new XMLHttpRequest();

        const degree = document.getElementById("degree").value;
        const player = localStorage.getItem("name");
        console.log(player);
        getRequest.open("GET", "http://localhost:82/sport/server/controller/controller.php?func=showDegree&degree=" + degree + "&player=" + player);
        getRequest.send(); 

        getRequest.onload = function() {

            const resultArray = JSON.parse(this.responseText);
            const table = document.getElementById('cols2');

            while(table.firstChild){
                table.removeChild(table.firstChild);
            }

            resultArray.sort((a, b) => {
                // Convert names to lowercase for case-insensitive sorting
                const nameA = a.name.toLowerCase();
                const nameB = b.name.toLowerCase();
        
                if (nameA < nameB) {
                    return -1; // a should be placed before b
                }
                if (nameA > nameB) {
                    return 1; // a should be placed after b
                }
                return 0; // names are equal
            });

            resultArray.forEach(element => {
                // create the row
                const newRow = document.createElement('tr');
                newRow.id = element.id;

                // create the category cell and add it to the row
                const nameCell = document.createElement('td');
                nameCell.textContent = element.name;
                newRow.appendChild(nameCell);

                 // create the category cell and add it to the row
                 const posCell = document.createElement('td');
                 posCell.textContent = element.position;
                 newRow.appendChild(posCell);

                 table.appendChild(newRow);
            });

        }

    }

}