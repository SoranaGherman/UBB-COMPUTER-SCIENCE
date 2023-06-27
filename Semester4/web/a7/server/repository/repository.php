<?php
require_once "../model/room.php";
require_once "../model/BookedRoom.php";

class Repository {
    private $hostname = "localhost";
    private $username = "root";
    private $password = "";
    private $database = "hotelchain";
    private $tableName = "room";
    private $tableName2 = "bookedRoom";
    private $connection = NULL;

    public function __construct(){
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

    public function selectAllRooms(int $pageSize, int $currentPage){
        $limit =  $pageSize;
        $offset = $pageSize * $currentPage;
        $sqlQuery = "SELECT * FROM `" . $this->tableName . "` LIMIT " . (string) $limit .
                    " OFFSET " . (string) $offset;

        $result  =  $this->connection->query($sqlQuery);
        if (! $result)
            return;

        $answerArray = array();

        if ($result->num_rows > 0){
        
            while($row = $result->fetch_assoc()){
                $room = new Room((int) $row["id"], $row["category"], $row["type"],
                                 (int )$row["price"], $row["hotel"]);
                array_push($answerArray, json_encode($room));
            }
        }

        return json_encode($answerArray);
    }

    public function selectAllBookedRooms(int $pageSize, int $currentPage){
        $limit =  $pageSize;
        $offset = $pageSize * $currentPage;
        $sqlQuery = "SELECT * FROM `" . $this->tableName2 . "` LIMIT " . (string) $limit .
                    " OFFSET " . (string) $offset;

        $result  =  $this->connection->query($sqlQuery);
        if (! $result)
            return;

        $answerArray = array();

        if ($result->num_rows > 0){
        
            while($row = $result->fetch_assoc()){
                $room = new BookedRoom((int) $row["id"], $row["category"], $row["type"],
                                 (int )$row["price"], $row["hotel"], $row["start"], $row["end"]);
                array_push($answerArray, json_encode($room));
            }
        }

        return json_encode($answerArray);
    }

    public function selectAllRoomsFiltered(int $pageSize, int $currentPage, string $category,
                                            string $type, int $price, string $hotel){
        $limit =  $pageSize;
        $offset = $pageSize * $currentPage;
        $sqlQuery = "SELECT * FROM `" . $this->tableName . "` WHERE `category` = '" . $category . "' AND
                    `type` = '" . $type . "' AND `price` = '" . $price . "' AND `hotel` = '" . $hotel . "'
                     LIMIT " . (string) $limit . " OFFSET " . (string) $offset;

        $result  =  $this->connection->query($sqlQuery);
        if (! $result)
            return;

        $answerArray = array();

        if ($result->num_rows > 0){
        
            while($row = $result->fetch_assoc()){
                $room = new Room((int) $row["id"], $row["category"], $row["type"],
                                 (int )$row["price"], $row["hotel"]);
                array_push($answerArray, json_encode($room));
            }
        }

        return json_encode($answerArray);
    }

    public function insertRoom(Room $room): bool{
        $sqlQuery = "INSERT INTO `" . $this->tableName . "` (`category`, `type`, `price`, `hotel`) 
                    VALUES (?, ?, ?, ?)";

        $statement = $this->connection->prepare($sqlQuery);

        $roomCateg = $room->getCategory();
        $roomType = $room->getType();
        $roomPrice = $room->getPrice();
        $roomHotel = $room->getHotel();

        $statement->bind_param("ssis", $roomCateg, $roomType, $roomPrice, $roomHotel);
        $statement->execute();

        return true;
    }

    public function insertBookedRoom(BookedRoom $room): bool{
        $sqlQuery = "INSERT INTO `" . $this->tableName2 . "` (`category`, `type`, `price`, `hotel`, `start`,`end`) 
                    VALUES (?, ?, ?, ?, ?, ?)";

        $statement = $this->connection->prepare($sqlQuery);

        $roomCateg = $room->getCategory();
        $roomType = $room->getType();
        $roomPrice = $room->getPrice();
        $roomHotel = $room->getHotel();
        $roomStart = $room->getStart();
        $roomEnd= $room->getEnd();

        $statement->bind_param("ssisss", $roomCateg, $roomType, $roomPrice, $roomHotel, $roomStart, $roomEnd);
        $statement->execute();

        return true;
    }


    public function updateRoom(int $roomId, Room $room) : bool{
        $sqlQuery = "UPDATE `" . $this->tableName . "` SET `category` = '" . $room->getCategory() . "',
                    `type` = '" . $room->getType() . "', `price` = '" . (string) $room->getPrice() . "',
                    `hotel` = '" . $room->getHotel() . "' WHERE `id` = '" . (string) $roomId . "'";
        
        if ($this->connection->query($sqlQuery) === TRUE)
            return true;

        return false;
    }

    public function deleteRoom(int $roomId) : bool{
        $sqlQuery = "DELETE FROM  `" . $this->tableName . "` WHERE `id` = '" .  $roomId . "'";
        
        if ($this->connection->query($sqlQuery) === TRUE)
            return true;

        return false;
    }

    public function deleteRoomBooked(int $roomId) : bool{
        $sqlQuery = "DELETE FROM  `" . $this->tableName2 . "` WHERE `id` = '" .  $roomId . "'";
        
        if ($this->connection->query($sqlQuery) === TRUE)
            return true;

        return false;
    }

}
?>