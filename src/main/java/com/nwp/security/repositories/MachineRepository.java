package com.nwp.security.repositories;

import com.nwp.security.user.Machine;
import com.nwp.security.user.User;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface MachineRepository {

    List<Machine> getAll();

    Optional<Machine> findById(Long id);

    Machine editMachine(Machine machine);

    Machine createMachine(Machine machine);

    void deleteById(Long id);

    List<Machine> findAllByCreatedBy(Optional<User> user);

    Collection<Machine> searchMachines(String name, List<String> statuses, LocalDate dateFrom, LocalDate dateTo);
}
