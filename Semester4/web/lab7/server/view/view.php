<?php

require_once "../service/service.php";

class View {
    private $service;
    public function __construct(){
        $this->service = new Service();
    }

    public function selectAllRoomsFiltered(int $currentPage, string $category, string $type,
                                            int $price, string $hotel){
        echo $this->service->selectAllRoomsFiltered($currentPage, $category, $type, $price, $hotel);
    }

    public function selectAllRooms(int $currentPage){
        echo $this->service->selectAllRooms($currentPage);
    }

    public function selectAllBookedRooms(int $currentPage){
        echo $this->service->selectAllBookedRooms($currentPage);
    }

    public function insertRoom(string $category, string $type, int $price, string $hotel){
        $this->service->insertRoom($category, $type, $price, $hotel);
    }

    public function insertBookedRoom(string $category, string $type, int $price, string $hotel, string $start, string $end){
        $this->service->insertBookedRoom($category, $type, $price, $hotel, $start, $end);
    }

    public function updateRoom(int $id, string $category, string $type, int $price, string $hotel){
        $this->service->updateRoom($id, $category, $type, $price, $hotel);
    }

    public function deleteRoom(int $id){
        $this->service->deleteRoom($id);
    }
    public function deleteRoomBooked(int $id){
        $this->service->deleteRoomBooked($id);
    }
}
?>