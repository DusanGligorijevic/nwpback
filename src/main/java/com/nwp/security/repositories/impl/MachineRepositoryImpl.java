package com.nwp.security.repositories.impl;

import com.nwp.security.repositories.MachineRepository;
import com.nwp.security.user.Machine;
import com.nwp.security.user.MachineRepositoryJpa;
import com.nwp.security.user.User;
import lombok.Builder;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
@Builder
public class MachineRepositoryImpl implements MachineRepository {

    private final MachineRepositoryJpa machineRepositoryJpa;

    @Override
    public List<Machine> getAll() {
        return machineRepositoryJpa.findAll();
    }

    @Override
    public Optional<Machine> findById(Long id) {
        return Optional.ofNullable(machineRepositoryJpa.findById(id).orElse(null));
    }

    @Override
    public Machine editMachine(Machine machine) {
        return machineRepositoryJpa.save(machine);
    }

    @Override
    public Machine createMachine(Machine machine) {
        return machineRepositoryJpa.save(machine);
    }

    @Override
    public void deleteById(Long id) {
        machineRepositoryJpa.deleteById(id);
    }

    @Override
    public List<Machine> findAllByCreatedBy(Optional<User> user) {
        return machineRepositoryJpa.findAllByCreatedBy(user.get());
    }

    @Override
    public Collection<Machine> searchMachines(String name, List<String> statuses, LocalDate dateFrom, LocalDate dateTo) {
        return machineRepositoryJpa.findByParams(name, statuses, dateFrom, dateTo);
    }
}
