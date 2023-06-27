<?php

require_once "../model/Player.php";
require_once "../model/Team.php";
require_once "../model/TeamMembers.php";

class Repo {
    private $hostname = "localhost:3306";
    private $username = "root";
    private $password = "";
    private $database = "sport";
    private $tableName = "Player";
    private $tableName2 = "Team";
    private $tableName3 = "TeamMembers";

    private $connection = NULL;

    public function __construct() {
        $this->connect();
    }

    public function __destruct() {
        $this->connection->close();
    }

    private function connect() {
        $this->connection = new mysqli($this->hostname, $this->username, $this->password, $this->database);
        
        // check connection
        if ($this->connection->connect_error) {
            die("Connection failed: " . $this->connection->connect_error);
        }

    }


    public function playerExists(string $player_name) {

        $ok = 0;


        $sqlQuery = "SELECT * FROM `" . $this->tableName . "` WHERE name= '" . $player_name . "';";
        
        $result = $this->connection->query($sqlQuery);

        
        if (!$result) {
            return;
        }
        
        if ($result->num_rows > 0) {
           $ok = 1;
        }
        
        return $ok;

    }

    public function filterPlayersByName(string $name){
        $sqlQuery = "SELECT * FROM `" . $this->tableName . "` WHERE `name` LIKE '%" . $name . "%' ";

        $result  =  $this->connection->query($sqlQuery);
        if (! $result)
            return;

        $answerArray = array();

        if ($result->num_rows > 0){
            while($row = $result->fetch_assoc()){
                $document = new Player((int) $row["id"], $row["name"], $row["position"]);
                array_push($answerArray, $document);
            }
        }

        return json_encode($answerArray);

    }

    public function showDegree1(string $name){
        $sqlQuery1 = "SELECT * FROM `" . $this->tableName . "` WHERE `name` =  '" . $name . "' ";
        
        $result  =  $this->connection->query($sqlQuery1);
        if (! $result)
            return;

        
        $row = $result->fetch_assoc();
        $id = (int)$row["id"];
        
        $sqlQuery2 = "SELECT * FROM `" . $this->tableName3 . "` WHERE `idPlayer1` =  '" . (string) $id . "' ";
       
        $result  =  $this->connection->query($sqlQuery2);
        
        if (! $result)
            return;
        
            $answerArray = array();

            if ($result->num_rows > 0){
                while($row = $result->fetch_assoc()){
                    $id_partner = (int) $row["idPlayer2"];
                    $sqlQuery3 = "SELECT * FROM `" . $this->tableName . "` WHERE `id` =  '" . (string) $id_partner . "' ";
                    $result  =  $this->connection->query($sqlQuery3);
                    if (! $result)
                        return;

                    $roww = $result->fetch_assoc();

                    $player = new Player((int) $roww["id"], $roww["name"], $roww["position"]);

                    array_push($answerArray, $player);
                }
            }
    
            return json_encode($answerArray);
    }

}

?>