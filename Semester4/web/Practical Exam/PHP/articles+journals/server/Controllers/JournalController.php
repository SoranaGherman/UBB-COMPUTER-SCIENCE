<?php

require_once '../Service/JournalService.php';

class JournalContoller 
{
    private $service;

    public function __construct()
    {
        $this->service = new JournalService();
    }
    
    public function handleRequest()
    {
        if (!isset($_SERVER["REQUEST_METHOD"]))
        {
            return;
        }

        $method = $_SERVER["REQUEST_METHOD"];   

        if ($method === "GET")
        {
            $this->handleGet();
            return;
        }

        if ($method === "POST")
        {
            $this->handlePost();
            return;
        }

        if ($method === "PUT")
        {
            $this->handlePut();
            return;
        }

        if ($method === "DELETE")
        {
            $this->handleDelete();
            return;
        }
    }

    private function handleDelete()
    {
        if (!isset($_GET["id"]))
        {
            echo "Invalid input";
            return;
        }

        $id = $_GET["id"];
        $this->service->delete($id);
    }

    private function handlePut()
    {
        $data = file_get_contents("php://input");
        $data = json_decode($data, true);

        if (!$this->validateInput($data, ["id", "name"]))
        {
            echo "Invalid input";
            return;
        }

        $this->service->update($data["id"], $data["name"]);
    }

    private function handlePost()
    {
        $data = file_get_contents("php://input");
        $data = json_decode($data, true);

        if (!$this->validateInput($data, ["name"]))
        {
            echo "Invalid input";
            return;
        }

        $this->service->add($data["name"]);
    }

    private function handleGet()
    {
        if (isset($_GET["id"]))
        {
            $this->getById($_GET["id"]);
            return;
        }

        $this->getAll();
    }

    private function getAll()
    {
        $books = $this->service->getAll();
        echo json_encode($books);
    }

    private function getById($id)
    {
        $book = $this->service->getById($id);
        echo json_encode($book);
    }

    private function validateInput($data, $fields) 
    {
        for ($i = 0; $i < count($fields); $i++)
        {
            if (!isset($data[$fields[$i]]))
            {
                echo "Missing field: " . $fields[$i] . "\n";
                return false;
            }
        }

        return true;
    }
}

$controller = new JournalContoller();
$controller->handleRequest();
?>