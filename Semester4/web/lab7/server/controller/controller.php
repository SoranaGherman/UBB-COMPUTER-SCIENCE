<?php

require_once "../view/view.php";

class Controller {
    private $view;

    public function __construct() {
        $this->view = new View();
    }

    public function serve(){
        
        if(!isset($_SERVER['REQUEST_METHOD'])){
            return;
        }

        if($_SERVER['REQUEST_METHOD'] === "POST"){
            if($_GET["which"] == "r"){
                $this->serveInsert();
                return;
            }

            if($_GET["which"] == "b"){
                $this->serveInsertBooked();
                return;
            }
        }

        if($_SERVER['REQUEST_METHOD'] === "DELETE"){
            if($_GET["which"] === "delR"){
                $this->serveDelete();
                return;
            }

            if($_GET["which"] === "bR"){
                $this->serveDeleteBooked();
                return;
            }
        }

        if($_SERVER['REQUEST_METHOD'] === "GET"){
            if($_GET["which"] === 'select' || ($_GET["which"] === 'filter' && 
                                               $_GET["category"] === 'all')){
                $this->serveSelect();
                return;
            }

            if($_GET["which"] === 'filter'){
                $this->serveSelectFilter();
                return;
            }

            if($_GET["which"] === 'booked'){
                $this->serveSelectBooked();
                return;
            }
        }

        if($_SERVER['REQUEST_METHOD'] === "PUT"){
            $this->serveUpdate();
            return;
        }

        
    }


    private function serveSelect() {
        if (!isset ($_GET["page"]))
            return;
    
        $this->view->selectAllRooms($_GET["page"]);
    }

    private function serveSelectBooked() {
        if (!isset ($_GET["page"]))
            return;
    
        $this->view->selectAllBookedRooms($_GET["page"]);
    }

    private function serveSelectFilter() {
        if (!isset ($_GET["page"]))
            return;
    
        $this->view->selectAllRoomsFiltered($_GET["page"], $_GET["category"], $_GET["type"],
                                            $_GET["price"], $_GET["hotel"]);
    }

    private function serveInsert() {
        if (!isset($_GET["category"]) || !isset($_GET["type"]) || !isset($_GET["price"]) || !isset($_GET["hotel"])){
            return;}
    
        $this->view->insertRoom($_GET["category"], $_GET["type"], (int) $_GET["price"], $_GET["hotel"]);
    }

    public function serveInsertBooked(){
        if (!isset($_GET["category"]) || !isset($_GET["type"]) || !isset($_GET["price"]) || !isset($_GET["hotel"])
            || !isset($_GET["start"]) || !isset($_GET["end"])){
            return;}
    
        $this->view->insertBookedRoom($_GET["category"], $_GET["type"], (int) $_GET["price"], $_GET["hotel"], $_GET["start"], $_GET["end"]);
    }

    private function serveUpdate() {
        if (!isset($_GET["roomId"]) || !isset($_GET["category"]) || !isset($_GET["type"])
            || !isset($_GET["price"]) || !isset($_GET["hotel"]))
            return;

        $this->view->updateRoom($_GET["roomId"], $_GET["category"], $_GET["type"], $_GET["price"],
                                $_GET["hotel"]);
    }

    private function serveDelete() {
        if (!isset($_GET["roomId"]))
            return;
        
        $this->view->deleteRoom((int) $_GET["roomId"]);
        
    }

    public function serveDeleteBooked(){
        if (!isset($_GET["roomId"]))
        return;
    
        $this->view->deleteRoomBooked((int) $_GET["roomId"]);
    }
}

$controller = new Controller();
$controller->serve();
?>