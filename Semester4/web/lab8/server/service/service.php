<?php

require_once "../model/room.php";
require_once "../repository/repository.php";

class Service {

    private $repository;

    public function __construct(){
        $this->repository = new Repository();
    }

    public function selectAllRooms(int $currentPage){
        return $this->repository->selectAllRooms(4, $currentPage);
    }

    public function selectAllBookedRooms(int $currentPage){
        return $this->repository->selectAllBookedRooms(4, $currentPage);
    }


    public function selectAllRoomsFiltered(int $currentPage, $category, $type, $price, $hotel){
        return $this->repository->selectAllRoomsFiltered(4, $currentPage, $category, $type, $price, $hotel);
    }

    public function insertRoom(string $category, string $type, int $price, string $hotel){
        $room = new Room(0, $category, $type, $price, $hotel);
        $this->repository->insertRoom($room);
    }

    public function insertBookedRoom(string $category, string $type, int $price, string $hotel, string $start, string $end){
        $room = new BookedRoom(0, $category, $type, $price, $hotel, $start, $end);
        $this->repository->insertBookedRoom($room);
    }

    public function updateRoom(int $id, string $category, string $type, int $price, string $hotel){
        $room = new Room(0, $category, $type, $price, $hotel);
        $this->repository->updateRoom($id, $room);
    }

    public function deleteRoom(int $id){
        $this->repository->deleteRoom($id);
    }

    public function deleteRoomBooked(int $id){
        $this->repository->deleteRoomBooked($id);
    }
}
?>