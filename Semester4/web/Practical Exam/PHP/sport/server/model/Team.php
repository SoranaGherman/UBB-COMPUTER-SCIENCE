<?php

class Team implements JsonSerializable{
    private int $id;
    private string $name;
    private string $homeCity;

    public function __construct(int $id, string $name, string $homeCity){
        $this->id = $id;
        $this->name = $name;
        $this->homeCity = $homeCity;
    }

    public function getId(): int{
        return $this->id;
    }

    public function getName(): string{
        return $this->name;
    }

    private function getHomeCity(): string{
        return $this->homeCity;
    }

    public function jsonSerialize(){
        return [
            "id" => $this->id,
            "name" => $this->name,
            "homeCity" => $this->homeCity,
        ];
    }

}
?>