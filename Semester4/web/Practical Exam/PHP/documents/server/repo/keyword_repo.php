<?php 

require_once "../model/Keyword.php";
require_once "../model/Document.php";
require_once "../model/Template.php";

class KeywordRepo {
    private $hostname = "localhost:3306";
    private $username = "root";
    private $password = "";
    private $database = "e22";
    private $tableName = "Keyword";
    private $tableName2 = "Document";
    private $tableName3 = "Template";

    private $tableName4 = "Login";

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

    public function getAll() {

    
        $sqlQuery = "SELECT * FROM `" . $this->tableName . "`;";
        $answerArray = array();


        $result = $this->connection->query($sqlQuery);
        if (!$result) {
            return;
        }
        
        if ($result->num_rows > 0) {
            // output data from each row
            while($row = $result->fetch_assoc()) {
                $keyword = new Keyword((int) $row["id"], $row["my_key"], $row["value"]);
                array_push($answerArray, $keyword);
            }
        }

        return json_encode($answerArray);

    }


    public function save(Keyword $keyword) {
        $sqlQuery = "INSERT INTO `" . $this->tableName . "` (`my_key`, `value`) VALUES (?, ?)";

        $statement = $this->connection->prepare($sqlQuery);

        $key = $keyword->getKey();
        $value = $keyword->getValue();

        $statement->bind_param("ss", $key, $value);
        $statement->execute();
    }

    public function filterDocuments(string $title){
        $sqlQuery = "SELECT * FROM `" . $this->tableName2 . "` WHERE `title` LIKE '%" . $title . "%' ";

        $result  =  $this->connection->query($sqlQuery);
        if (! $result)
            return;

        $answerArray = array();

        if ($result->num_rows > 0){
            while($row = $result->fetch_assoc()){
                $document = new Document((int) $row["id"], $row["title"], $row["templates"]);
                array_push($answerArray, $document);
            }
        }

        return json_encode($answerArray);
    }

    public function getAllDocuments() {

        $sqlQuery = "SELECT * FROM `" . $this->tableName2 . "`;";
        $answerArray = array();

        $result = $this->connection->query($sqlQuery);
        if (!$result) {
            return;
        }
        
        if ($result->num_rows > 0) {
            // output data from each row
            while($row = $result->fetch_assoc()) {
                $keyword = new Keyword((int) $row["id"], $row["title"], $row["templates"]);
                array_push($answerArray, json_encode($keyword));
            }
        }
        else {
            return $answerArray;
        } 

        return json_encode($answerArray);

    }

    public function getAllTemplates(string $ids){

        $sqlQueryK = "SELECT * FROM `" . $this->tableName . "`;";
        $answerArrayK = array();


        $result = $this->connection->query($sqlQueryK);
        if (!$result) {
            return;
        }
        
        if ($result->num_rows > 0) {
            // output data from each row
            while($row = $result->fetch_assoc()) {
                $keyword = new Keyword((int) $row["id"], $row["my_key"], $row["value"]);
                array_push($answerArrayK, $keyword);
            }
        }

        $template_ids = explode(' ', $ids);
        $commaSeparatedIds = implode(', ', $template_ids);

        $sqlQuery = "SELECT * FROM `" . $this->tableName3 . "` WHERE id IN ( " . $commaSeparatedIds . " );";
        $answerArray = array();

        $result = $this->connection->query($sqlQuery);
        if (!$result) {
            return;
        }
        
        if ($result->num_rows > 0) {
            // output data from each row
            while($row = $result->fetch_assoc()) {

                $text = $row["textContent"];

                foreach ($answerArrayK as $keys) {
                    $key = "{{" . $keys->getKey() . "}}";
                    $value = $keys->getValue();
                    $text = str_replace($key, $value, $text);
                }

                $keyword = new Template((int) $row["id"], $row["name"], $text);
                array_push($answerArray, $keyword);

            }
        }

        return json_encode($answerArray);        
    
    }


    public function logIn(string $username, string $password){
        $sqlQuery = "SELECT * FROM $this->tableName4 WHERE username = ? AND password = ?";

        $statement = $this->connection->prepare($sqlQuery);

        $statement->bind_param("ss", $username, $password);
        $statement->execute();

        $result = $statement->get_result();
        if (!$result) {
            return 0;
        }
        
        if ($result->num_rows > 0){
            return 1;
        }
        
        return 0;
        
    }
}
?>