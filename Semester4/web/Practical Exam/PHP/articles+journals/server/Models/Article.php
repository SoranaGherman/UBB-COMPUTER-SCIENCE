<?php

class Article implements JsonSerializable
{
    public $id;
    public $user;
    public $journalId;
    public $summary;
    public $date;

    public function __construct($id, $user, $journalId, $summary, $date)
    {
        $this->id = $id;
        $this->user = $user;
        $this->journalId = $journalId;
        $this->summary = $summary;
        $this->date = $date;
    }

    public function jsonSerialize(): mixed
    {
        return [
            'id' => $this->id,
            'user' => $this->user,
            'journalId' => $this->journalId,
            'summary' => $this->summary,
            'date' => $this->date
        ];
    }
} 

?>