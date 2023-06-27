class EventHandling {
    constructor(){

        const thisObject = this;

        document.getElementById("search_player_button").addEventListener("click", this.searchPlayerButton);
    }

    searchPlayerButton(event){
        event.preventDefault(); // Prevent form submission and page refresh
        console.log("ok");
        const player_name = document.getElementById("player_name").value;
        localStorage.setItem("name", player_name);
        
        if(player_name == ""){
            window.alert("Please fill in all fields!");
            return;
        }

       
        const getRequest = new XMLHttpRequest();
    
        getRequest.open("GET", "http://localhost:82/sport/server/controller/controller.php?func=search&player_name=" + player_name);
        getRequest.send();   

        getRequest.onload = function() {

            const result = this.responseText;
            console.log(result);
            if(result === "0"){
                window.alert("This player does not exist!");
                return;
            }

            document.location.href = "./index.html";
        }
        
    }
}