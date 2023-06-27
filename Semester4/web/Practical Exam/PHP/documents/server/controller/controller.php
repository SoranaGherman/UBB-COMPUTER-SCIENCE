<?php

require_once "../model/Keyword.php";
require_once "../repo/keyword_repo.php";

class Controller {
    private $keyword_repo;

    public function __construct() {
        $this->keyword_repo = new KeywordRepo();
    }

    public function serve() {

        session_start();

        if (isset($_GET) && isset($_GET["func"]) && $_GET["func"] === "login") {
            $this->serveLogin();
            return;
        }
        
        # Anything below this line requires a logged in user
        if (!isset($_SESSION["user"])) {
            echo "401";
            return;
        }

        # Logout function
        if (isset($_GET["func"]) && $_GET["func"] === "logout") {
            $this->serveLogout();
            return;
        }

        if(isset($_POST["func"]) && $_POST["func"] === "add"){
            $this->serveAdd();
        }

        if(isset($_GET["func"]) && $_GET["func"] === "getKeyword"){
            $this->serveGetAllKeywords();
        }

        if(isset($_GET["func"]) && $_GET["func"] === "getTemplates"){
            $this->serveGetAllTemplates();
        }


        if(isset($_GET["func"]) && $_GET["func"] === "getDocuments"){
            $this->serveFilterDocuments();
        }

        

    }

    private function serveGetAllTemplates() {
        if (!isset($_GET["ids"])) {
            return;
        }

        echo $this->keyword_repo->getAllTemplates($_GET["ids"]);
    }

    private function serveGetAllKeywords(){

        echo $this->keyword_repo->getAll();
    }

    private function serveAdd() {

        if (!isset($_POST["key"]) || !isset($_POST["value"])) {
            return;
        }

        $this->keyword_repo->save(new Keyword(-1, $_POST["key"], $_POST["value"]));
    }

    private function serveFilterDocuments() {
        if (!isset($_GET["title"])){
            return;
        }

        echo $this->keyword_repo->filterDocuments($_GET["title"]);
    }

    private function serveLogin() {
        
        if (!isset($_GET["user"]) || !isset($_GET["password"])) {
            echo "user or password not set";
            return;
        }

        $verified =  $this->keyword_repo->logIn($_GET["user"], $_GET["password"]);

        if ($verified === "1" or $verified === 1) {
            $_SESSION["user"] = $_GET["user"];
        }

        echo $verified;
    }

    private function serveLogout() {
        unset($_SESSION["user"]);
        echo "1";
    }
}


$controller = new Controller();
$controller->serve();

?>
