package com.nwp.security.services.impl;

import com.nwp.security.repositories.ErrorMessageRepository;
import com.nwp.security.repositories.MachineRepository;
import com.nwp.security.repositories.UserRepository;
import com.nwp.security.user.ErrorMessage;
import com.nwp.security.user.Machine;
import com.nwp.security.user.enums.Status;
import jakarta.transaction.Transactional;
import lombok.Builder;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.annotation.Async;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.*;
import java.util.logging.Logger;

@Builder

public class MachineServiceImpl implements MachineService {


//    private static final Logger logger = (Logger) LoggerFactory.getLogger(MachineServiceImpl.class);
    private final MachineRepository machineRepository;
    private final UserRepository userRepository;
    private TaskScheduler taskScheduler;
    private ErrorMessageRepository errorMessageRepository;


    @Override
    public List<Machine> getAll() {
        return machineRepository.getAll();
    }

    @Override
    @Transactional
    public Optional<Machine> findById(Long id) {
        return machineRepository.findById(id);
    }

    @Override
    public Machine editMachine(Machine machine) {
        return machineRepository.editMachine(machine);
    }

    @Override
    public void deleteById(Long id) {
        machineRepository.deleteById(id);
    }


    @Override
    @Transactional
    public Collection<Machine> getMachinesByUser(String userMail) {
        return machineRepository.findAllByCreatedBy(userRepository.findByEmail(userMail));
    }


    /*

    @Query(value = "SELECT * FROM Users u WHERE u.status = :status and u.name = :name",
    nativeQuery = true)
    User findUserByStatusAndNameNamedParamsNative(
     @Param("status") Integer status, @Param("name") String name);


     */




//    @Override
//    @Transactional
//    public Collection<Machine> searchMachines(String name, List<String> statuses, LocalDate dateFrom, LocalDate dateTo, String userMail) {
//        ArrayList<Machine> allMachinesByUser = (ArrayList<Machine>) getMachinesByUser(userMail);
//        ArrayList<Machine> filteredMachines = new ArrayList<>();
//        int addFlag;
//
//        for (Machine machine : allMachinesByUser) {
//            addFlag = 0;
//
//            if (name != null && machine.getName().toLowerCase().contains(name.toLowerCase())) addFlag++; //flag == 1
//            else if (name == null) addFlag++;
//
//            if (statuses != null && statuses.contains(machine.getStatus().toString())) addFlag++; //flag == 2
//            else if (statuses == null) addFlag++;
//
//            if (dateFrom != null && dateTo != null && machine.getCreationDate().isAfter(dateFrom) && machine.getCreationDate().isBefore(dateTo))
//                addFlag++; //flag == 3
//            else if (dateFrom == null || dateTo == null) addFlag++;
//
//            if (addFlag == 3) filteredMachines.add(machine);
//        }
//        return filteredMachines;
//    }

        @Override
    @Transactional
    public Collection<Machine> searchMachines(String name, List<String> statuses, LocalDate dateFrom, LocalDate dateTo, String userMail) {
         return machineRepository.searchMachines(name, statuses, dateFrom, dateTo);
        }

    @Override
    public Machine createMachine(String name, String userMail) {
        return machineRepository.createMachine(
                Machine.builder()
                        .id(1L)
                        .status(Status.STOPPED)
                        .createdBy(userRepository.findByEmail(userMail).get())
                        .active(true)
                        .name(name)
                        .creationDate(LocalDate.now())
                        .build());
    }

    @Transactional
    public Collection<ErrorMessage> findAllErrorsForUser(String userMail){
        return errorMessageRepository.findAllByMachine_CreatedBy(userRepository.findByEmail(userMail));
    }

    @Override
    @Transactional
    public void destroyMachine(Long id) {
        System.err.println("destroying machine");
        Optional<Machine> optionalMachine = this.findById(id);
        if (optionalMachine.isEmpty()) {
//            logger.info("There is no machine with provided id.");
            return;
        }
            Machine machine = optionalMachine.get();
            if (machine.getStatus() != Status.STOPPED)
                return;
            machine.setActive(false);
            machineRepository.createMachine(machine);
    }

    @Override
    @Async
    @Transactional
    public void startMachine(Long id, boolean scheduled) throws InterruptedException {
        Optional<Machine> optionalMachine = machineRepository.findById(id);
        if(optionalMachine.isEmpty()) {
//            logger.info("There is no machine with provided id.");
            return;
        }
            Machine machine = optionalMachine.get();
            if(machine.isActive()) {
                if (machine.getStatus() == Status.STOPPED) {
//                    logger.info("Starting machine...");
                    Thread.sleep((long) (Math.random() * (12000 - 10000) + 10000));
                    machine.setStatus(Status.RUNNING);
                    machineRepository.createMachine(machine);
//                    logger.info("Machine started!");
                } else
                if(scheduled)
                    errorMessageRepository.save(new ErrorMessage(0L, "The machine's status is not 'STOPPED'.", "START", LocalDate.now(), machine));
            } else
            if(scheduled)
                errorMessageRepository.save(new ErrorMessage(0L, "The machine is deactivated.", "START",LocalDate.now(), machine));

    }


    @Override
    @Async
    @Transactional
    public void stopMachine(Long id, boolean scheduled) throws InterruptedException {
        Optional<Machine> optionalMachine = machineRepository.findById(id);
        if(optionalMachine.isPresent()) {
            Machine machine = optionalMachine.get();
            if(machine.isActive()) {
                if (machine.getStatus() == Status.RUNNING) {
//                    logger.info("Stopping machine...");
                    Thread.sleep((long) (Math.random() * (12000 - 10000) + 10000));
                    machine.setStatus(Status.STOPPED);
                    machineRepository.createMachine(machine);
//                    logger.info("Machine stopped!");
                } else
                if(scheduled)
                    errorMessageRepository.save(new ErrorMessage(0L, "The machine's status is not 'RUNNING'.", "STOP", LocalDate.now(), machine));
            } else
            if(scheduled)
                errorMessageRepository.save(new ErrorMessage(0L, "The machine is deactivated.", "STOP",LocalDate.now(), machine));
        }
    }

    @Override
    @Async
    @Transactional
    public void restartMachine(Long id, boolean scheduled) throws InterruptedException {
        Optional<Machine> optionalMachine = machineRepository.findById(id);
        if(optionalMachine.isPresent()) {
            Machine machine = optionalMachine.get();
            if(machine.isActive()) {
                if (machine.getStatus() == Status.RUNNING) {
//                   logger.info("Stopping machine for restart...");
                    Thread.sleep((long) (Math.random() * (10000 - 5000) + 5000));
                    machine.setStatus(Status.STOPPED);
                    machineRepository.createMachine(machine);

                    machine = this.findById(id).get();

//                    logger.info("Starting machine for restart...");
                    Thread.sleep((long) (Math.random() * (10000 - 5000) + 5000));
                    machine.setStatus(Status.RUNNING);
                    machineRepository.createMachine(machine);
//                    logger.info("Machine restarted!");
                } else
                if(scheduled)
                    errorMessageRepository.save(new ErrorMessage(0L, "The machine's status is not 'RUNNING'.", "RESTART", LocalDate.now(), machine));
            } else
            if(scheduled)
                errorMessageRepository.save(new ErrorMessage(0L, "The machine is deactivated.", "RESTART",LocalDate.now(), machine));
        }
    }

    @Override
    @Transactional
    public void scheduleMachine(Long id, String date, String time, String action) throws ParseException {
        Date date1 = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").parse(date + " " + time);
//        logger.info("Machine scheduled for " + date1);

        this.taskScheduler.schedule(() -> {
            try {
                switch (action) {
                    case "Start":
                        startMachine(id, true);
                        break;
                    case "Stop":
                        stopMachine(id, true);
                        break;
                    case "Restart":
                        restartMachine(id, true);
                        break;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }, date1);
    }
}
