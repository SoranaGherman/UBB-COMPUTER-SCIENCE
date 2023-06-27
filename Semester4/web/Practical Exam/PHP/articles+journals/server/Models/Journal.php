<?php
class Journal implements JsonSerializable 
{
    public $id;
    public $name;
    
    public function __construct($id, $name)
    {
        $this->id = $id;
        $this->name = $name;
    }

    public function jsonSerialize(): mixed
    {
        return [
            'id' => $this->id,
            'name' => $this->name
        ];
    }
}
?>