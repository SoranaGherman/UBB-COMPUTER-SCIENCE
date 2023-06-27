class EventHandling {
    constructor(){

        const thisObject = this;

        document.getElementById("login_button").addEventListener("click", function(){
            thisObject.login();
        });
    }


    login(){
        const getRequest = new XMLHttpRequest();
    
        const user = document.getElementById("username").value;
        const password = document.getElementById("password").value;

        getRequest.open("GET", "../../server/controller/controller.php?func=login&user=" + user + "&password=" + password);
        getRequest.send();   
        
        getRequest.onload = function() {

            const response = this.responseText;

            console.log(`${response} -- RESPONSE`);
            
            if (response == 1) {
                localStorage.setItem("user", user);
                window.location.href = "../pages/main.html";
                return;
            }

            if (response == 0) {
                alert("Wrong username or password!");
                return;
            }

            alert("Something went wrong!");
        }

    }
}
   
