<?php
class BookedRoom implements JsonSerializable {
    private $id;
    private $category;
    private $type;
    private $price;
    private $hotel;

    private $start;
    private $end;

    public function __construct(int $id, string $categry, string $type, int $price, string $hotel,
                                string $start, string $end){
        $this->id = $id;
        $this->category = $categry;
        $this->type = $type;
        $this->price = $price;
        $this->hotel = $hotel;
        $this->start = $start;
        $this->end = $end;
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

    public function getStart(): string{
        return $this->start;
    }

    public function getEnd(): string{
        return $this->end;
    }

    public function jsonSerialize(){
        return [
            'id' => $this->id,
            'category' => $this->category,
            'type' => $this->type,
            'price' => $this->price,
            'hotel' => $this->hotel,
            'start' => $this->start,
            'end'  => $this->end,
        ];
    }
}
?>