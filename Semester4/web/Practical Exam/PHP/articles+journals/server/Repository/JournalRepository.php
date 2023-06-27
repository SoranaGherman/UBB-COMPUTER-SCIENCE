<?php

require_once "../Models/Journal.php";
require_once "../Repository/Repository.php";

class JournalRepository extends Repository
{
    private $table = "journals";

    public function __construct()
    {
        parent::__construct();
    }

    public function __destruct()
    {
        parent::__destruct();
    }

    public function getAll(): array
    {
        $sql = "SELECT * FROM $this->table";
        $stmt = $this->connection->prepare($sql);
        
        $stmt->execute();

        $result = $stmt->get_result();

        $elements = [];

        if ($result && $result->num_rows > 0) 
        {
            while($row = $result->fetch_assoc()) 
            {
                $element = new Journal($row["id"], $row["name"]);
                array_push($elements, $element);
            }
        }

        return $elements;
    }

    public function getByName($name): ?Journal
    {
        $sql = "SELECT * FROM $this->table WHERE name = ?";
        $stmt = $this->connection->prepare($sql);

        $stmt->bind_param('s', $name);

        $stmt->execute();

        $result = $stmt->get_result();

        if (!($result && $result->num_rows === 1))
        {
            return null;
        }

        $row = $result->fetch_assoc();
        $element = new Journal($row["id"], $row["name"]);

        return $element;
    }

    public function getById($id): ?Journal
    {
        $sql = "SELECT * FROM $this->table WHERE id = ?";
        $stmt = $this->connection->prepare($sql);
            
        $stmt->bind_param("i", $id);
        $stmt->execute();
    
        $result = $stmt->get_result();

        $element = null;
        if ($result && $result->num_rows === 1)
        {
            $row = $result->fetch_assoc();
            $element = new Journal($row["id"], $row["name"]);
        }
    
        $stmt->close();
    
        return $element;
    }

    public function add(Journal $element): bool
    {
        $sql = "INSERT INTO $this->table (id, name) VALUES (?, ?)";

        $stmt = $this->connection->prepare($sql);

        $stmt->bind_param("is", $element->id, $element->name);
        $result = $stmt->execute();

        $stmt->close();

        return $result;
    }

    public function update(Journal $element): bool
    {
        $sql = "UPDATE $this->table SET name = ? WHERE id = ?";

        $stmt = $this->connection->prepare($sql);
        
        $stmt->bind_param("si", $element->name, $element->id);
        $result = $stmt->execute();

        $stmt->close();

        return $result;
    }

    public function delete(int $id): bool
    {
        $sql = "DELETE FROM $this->table WHERE id = ?";

        $stmt = $this->connection->prepare($sql);

        $stmt->bind_param("i", $id);
        $result = $stmt->execute();

        $stmt->close();

        return $result;
    }
}
?>