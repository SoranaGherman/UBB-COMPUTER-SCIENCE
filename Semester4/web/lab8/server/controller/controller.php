<?php
 
header('Access-Control-Allow-Origin: *');
header('Access-Control-Allow-Headers: *');
header('Access-Control-Allow-Methods: *');
header('Content-type: application/json');

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

        if ($_GET["func"] === "insert"){
            $this->serveInsert();
        }

        if ($_GET["func"] === "book"){
            $this->serveInsertBooked();
        }

        if($_SERVER['REQUEST_METHOD'] === "DELETE"){
            if($_GET["func"] === "deleteRoom"){
                $this->serveDelete();
                return;
            }

            if($_GET["func"] === "cancelRoom"){
                $this->serveDeleteBooked();
                return;
            }
        }

        if($_SERVER['REQUEST_METHOD'] === "GET"){
            if($_GET["func"] === 'selectAll'){
                $this->serveSelect();
                return;
            }

            if($_GET["func"] === 'selectFiltered'){
                $this->serveSelectFilter();
                return;
            }

            if($_GET["func"] === 'selectBooked'){
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

        if ($_GET["category"] === "all"){
            $this->view->selectAllRooms($_GET["page"]);
            return;
        }
    
        $this->view->selectAllRoomsFiltered($_GET["page"], $_GET["category"], $_GET["type"],
                                            $_GET["price"], $_GET["hotel"]);
    }

    private function serveInsert() {
        $json = file_get_contents("php://input");
        $data = json_decode($json, true);

        if (!isset($data["category"]) || !isset($data["type"]) || !isset($data["price"]) || !isset($data["hotel"])){
            return;}
    
        $this->view->insertRoom($data["category"], $data["type"], (int) $data["price"], $data["hotel"]);
    }

    public function serveInsertBooked(){
        $json = file_get_contents("php://input");
        $data = json_decode($json, true);

        if (!isset($data["category"]) || !isset($data["type"]) || !isset($data["price"]) || !isset($data["hotel"])
            || !isset($data["start"]) || !isset($data["end"])){
            return;}
    
        $this->view->insertBookedRoom($data["category"], $data["type"], (int) $data["price"], $data["hotel"], $data["start"], $data["end"]);
    }

    private function serveUpdate() {
        $json = file_get_contents("php://input");
        $data = json_decode($json, true);


        if (!isset($data["id"]) || !isset($data["category"]) || !isset($data["type"])
            || !isset($data["price"]) || !isset($data["hotel"]))
            return;


        $this->view->updateRoom($data["id"], $data["category"], $data["type"], (int) $data["price"],
                                $data["hotel"]);
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