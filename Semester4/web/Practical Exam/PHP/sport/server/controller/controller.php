<?php

require_once "../model/Player.php";
require_once "../model/Team.php";
require_once "../model/TeamMembers.php";
require_once "../repo/repo.php";

class Controller {
    private $repo;

    public function __construct() {
        $this->repo = new Repo();
    }

    public function serve() {
        
        if(isset($_GET["func"]) && $_GET["func"] === "search"){
            $this->serveSearchPlayer();
        }

        if(isset($_GET["func"]) && $_GET["func"] === "filterPlayers"){
            $this->serveFilterPlayersByName();
        }

        if(isset($_GET["func"]) && $_GET["func"] === "showDegree"){
            $this->serveshowDegree();
        }
    }

    public function serveSearchPlayer(){
        if (!isset($_GET["player_name"])) {
            return;
        }

        echo $this->repo->playerExists($_GET["player_name"]);
    }

    public function serveFilterPlayersByName(){

        if (!isset($_GET["name_player"])) {
            return;
        }

        echo $this->repo->filterPlayersByName($_GET["name_player"]);

    }

    public function serveshowDegree(){
        if (!isset($_GET["degree"]) || !isset($_GET["player"])) {
            return;
        }
        
        if($_GET["degree"] == "1"){
            
            echo $this->repo->showDegree1($_GET["player"]);
        }

    }
}

$controller = new Controller();
$controller->serve();
