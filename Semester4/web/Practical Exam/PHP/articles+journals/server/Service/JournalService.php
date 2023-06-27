<?php

require_once '../Repository/JournalRepository.php';
require_once '../Models/Journal.php';

class JournalService 
{
    private $repository;

    public function __construct()
    {
        $this->repository = new JournalRepository();
    }

    public function getAll(): array
    {
        return $this->repository->getAll();
    }

    public function getById($id): Journal
    {
        return $this->repository->getById($id);
    }

    public function add($name): void
    {
        $this->repository->add(new Journal(null, $name));
    }

    public function update($id, $name): void
    {
        $this->repository->update(new Journal($id, $name));
    }

    public function delete($id): void
    {
        $this->repository->delete($id);
    }
}
?>