<?php

require_once "../Models/Journal.php";
require_once "../Models/Article.php";
require_once "../Repository/Repository.php";
require_once "../Repository/JournalRepository.php";

class ArticlesRepository extends Repository
{
    private $table = "articles";
    private $journalRepository;

    public function __construct()
    {
        parent::__construct();
        $this->journalRepository = new JournalRepository();
    }

    public function __destruct()
    {
        parent::__destruct();
    }

    public function loginUser($name) 
    {
        $sql = "SELECT * FROM $this->table WHERE user = ?";
        $stmt = $this->connection->prepare($sql);

        $stmt->bind_param('s', $name);
        $stmt->execute();

        $sql_result = $stmt->get_result();

        if ($sql_result && $sql_result->num_rows > 0) 
        {
            return 1;
        }

        return 0;
    }

    public function getAll($user, $journalId): array
    {
        $sql = "SELECT * FROM $this->table WHERE user = ? and journalId = ?";
        $stmt = $this->connection->prepare($sql);

        $stmt->bind_param('si', $user, $journalId);
        $stmt->execute();

        $result = $stmt->get_result();

        if (!($result && $result->num_rows > 0))
        {
            return [];
        }

        $elements = [];
        while($row = $result->fetch_assoc()) 
        {
            $element = new Article($row["id"], $row["user"], $row["journalId"], $row["summary"], $row["date"]);
            array_push($elements, $element);
        }

        return $elements;
    }

    public function getAllW(): array
    {
        $sql = "SELECT * FROM $this->table";
        $stmt = $this->connection->prepare($sql);

        $stmt->execute();

        $result = $stmt->get_result();

        if (!($result && $result->num_rows > 0))
        {
            return [];
        }

        $elements = [];
        while($row = $result->fetch_assoc()) 
        {
            $element = new Article($row["id"], $row["user"], $row["journalId"], $row["summary"], $row["date"]);
            array_push($elements, $element);
        }

        return $elements;
    }

    public function add($user, $journalId, $summary): bool
    {
        $sql = "INSERT INTO $this->table (user, journalId, summary, date) VALUES (?, ?, ?, ?)";

        $stmt = $this->connection->prepare($sql);
        
        $t = date('d-m-Y');
        $day = (int)strtolower(date("d",strtotime($t)));
        $stmt->bind_param("sssi", $user, $journalId, $summary, $day);
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