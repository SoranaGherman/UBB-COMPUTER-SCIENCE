<?php
class Room implements JsonSerializable {
    private $id;
    private $category;
    private $type;
    private $price;
    private $hotel;

    public function __construct(int $id, string $categry, string $type, int $price, string $hotel){
        $this->id = $id;
        $this->category = $categry;
        $this->type = $type;
        $this->price = $price;
        $this->hotel = $hotel;
    }

    public function getCategory(): string{
        return $this->category;
    }

    public function getType(): string{
        return $this->type;
    }

    public function getPrice(): int{
        return $this->price;
    }

    public function getHotel(): string{
        return $this->hotel;
    }

    public function jsonSerialize(){
        return [
            'id' => $this->id,
            'category' => $this->category,
            'type' => $this->type,
            'price' => $this->price,
            'hotel' => $this->hotel,
        ];
    }
}
?>