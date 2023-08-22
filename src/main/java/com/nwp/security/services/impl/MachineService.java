package com.nwp.security.services.impl;

import com.nwp.security.user.ErrorMessage;
import com.nwp.security.user.Machine;

import java.text.ParseException;
import java.time.LocalDate;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface MachineService {

    List<Machine> getAll();

    Optional<Machine> findById(Long id);

    Machine editMachine(Machine machine);

    void deleteById(Long id);

    Machine createMachine(String name, String userMail);

     Collection<ErrorMessage> findAllErrorsForUser(String userMail);

    Collection<Machine> getMachinesByUser(String userMail);
    Collection<Machine> searchMachines(String name, List<String> statuses, LocalDate dateFrom, LocalDate dateTo, String userMail);
    void destroyMachine(Long id);
    void startMachine(Long id,boolean scheduled) throws InterruptedException;
    void stopMachine(Long id, boolean scheduled) throws InterruptedException;
    void restartMachine(Long id, boolean scheduled) throws InterruptedException, ParseException;
    void scheduleMachine(Long id, String date, String time, String action) throws ParseException;

}
