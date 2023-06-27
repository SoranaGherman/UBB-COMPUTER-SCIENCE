<?php

class TeamMembers implements JsonSerializable{
    private int $id;
    private int $idPlayer1;
    private int $idPlayer2;

    private int $idTeam;



    public function __construct(int $id, int $idPlayer1, int $idPlayer2){
        $this->id = $id;
        $this->idPlayer1 = $idPlayer1;
        $this->idPlayer2 = $idPlayer2;
    }

    public function getId(): int{
        return $this->id;
    }

    public function getIdPlayer1(): int{
        return $this->idPlayer1;
    }

    public function getIdPlayer2(): int{
        return $this->idPlayer2;
    }

    public function getIdTeam(): int{
        return $this->idTeam;
    }

    public function jsonSerialize(){
        return [
            "id" => $this->id,
            "idPlayer1" => $this->idPlayer1,
            "idPlayer2" => $this->idPlayer2,
            "idTeam" => $this->idTeam,

        ];
    }

}
?>