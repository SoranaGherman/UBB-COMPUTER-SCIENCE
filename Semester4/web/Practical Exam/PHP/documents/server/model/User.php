<?php

class Usere implements JsonSerializable {
    private $id;
    private $username;
    private $password;

    public function __construct(int $id, string $username, string $password){
        $this->id = $id;
        $this->username = $username;
        $this->password = $password;
    }

    public function getId(): int{
        return $this->id;
    }

    public function getUsername(): string {
        return $this->username;
    }

    public function getPassword(): string {
        return $this->password;
    }

    public function jsonSerialize() {
        return [
             "id" => $this->id,
             "username" => $this->username,
             "password" => $this->password,
        ];

    }
}


?>