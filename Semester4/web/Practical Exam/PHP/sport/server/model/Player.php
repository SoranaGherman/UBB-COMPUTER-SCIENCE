<?php

class Player implements JsonSerializable{
    private int $id;
    private string $name;
    private string $position;

    public function __construct(int $id, string $name, string $position){
        $this->id = $id;
        $this->name = $name;
        $this->position = $position;
    }

    public function getId(): int{
        return $this->id;
    }

    public function getName(): string{
        return $this->name;
    }

    private function getPosition(): string{
        return $this->position;
    }

    public function jsonSerialize(){
        return [
            "id" => $this->id,
            "name" => $this->name,
            "position" => $this->position,
        ];
    }

}
?>