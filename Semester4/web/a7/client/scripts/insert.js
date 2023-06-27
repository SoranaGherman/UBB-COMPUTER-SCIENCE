class EventHandling {
    constructor() {
        document.getElementById("home-page").addEventListener("click", this.homeBtnClicked);
        document.getElementById("db-insert").addEventListener("click", this.dbInsertBtnClicked);
        document.getElementById("db-update").addEventListener("click", this.dbUpdateBtnClicked);
        document.getElementById("db-delete").addEventListener("click", this.dbDeleteBtnClicked);

        document.getElementById("submit-insert-btn").addEventListener("click", this.submitBtnClicked);
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

    submitBtnClicked(event) {
        const category = document.getElementById("room-category").value;
        const type = document.getElementById("room-type").value;
        const price = document.getElementById("room-price").value;
        const hotel = document.getElementById("room-hotel").value;

        if(category === "" || type === "" || price === "" || hotel === ""){
            window.alert("Please fill in all attributes before inserting!");
            return;
        }

        if(price < 0)
        {
            window.alert("Price must be greater than zero!");
            return;
        }

        const postRequest = new XMLHttpRequest();

        postRequest.open("POST", "../../server/controller/controller.php?which=r&category="+ category + "&type=" + type + "&price=" + price + "&hotel=" + hotel);
        postRequest.send();

        window.alert("Room was successfully inserted! :)");
    }
}