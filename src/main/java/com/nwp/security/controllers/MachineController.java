package com.nwp.security.controllers;

import com.nwp.security.repositories.MachineRepository;
import com.nwp.security.services.UserService;
import com.nwp.security.services.impl.MachineService;
import com.nwp.security.user.Machine;
import com.nwp.security.user.enums.Status;
import jakarta.websocket.server.PathParam;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
@RestController
@RequestMapping("machines")
@RequiredArgsConstructor
@Data
public class MachineController {
    private final MachineService machineService;
    private final UserService userService;
    private final MachineRepository machineRepository;



    @GetMapping(value = "/get_filtered", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Collection<Machine>> getMachinesFiltered(
            @PathParam("mail") String mail,
            @PathParam("name") String name,
            @PathParam("status") String status,
            @PathParam("dateFrom") String dateFrom,
            @PathParam("dateTo") String dateTo){

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate parsedFrom = null;
        LocalDate parsedTo = null;
        List<String> machineFilterStatuses = null;

        if (dateFrom != null) parsedFrom = LocalDate.parse(dateFrom, formatter);
        if (dateTo != null) parsedTo = LocalDate.parse(dateTo, formatter);
        if (status != null) machineFilterStatuses = new ArrayList<>(Arrays.asList(status.split(",")));

        return ResponseEntity.ok().body(machineService.searchMachines(name, machineFilterStatuses, parsedFrom, parsedTo, mail));
    }

    @GetMapping(value = "/get", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Collection<Machine>> getMachinesByUser(@PathParam("mail") String mail){
        return ResponseEntity.ok().body(machineService.getMachinesByUser(mail));
    }

    @PostMapping(value = "/create", produces = MediaType.APPLICATION_JSON_VALUE)
    public Machine createMachine(@RequestBody CreateRequest createRequest){
        System.err.println(createRequest.getName() + " --- " + createRequest.getMail());
        return machineService.createMachine(createRequest.getName(), createRequest.getMail());
    }

    @DeleteMapping(value = "/destroy/{id}")
    private ResponseEntity<?> destroyMachine(@PathVariable("id") Long id){
        machineService.destroyMachine(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
    @MessageMapping("/hello")
    @SendTo("/topic/greetings")

    @GetMapping(value = "/start/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> startMachine(@PathVariable Long id) throws InterruptedException {

        Optional<Machine> optionalMachine = machineService.findById(id);

        if(optionalMachine.isPresent() && optionalMachine.get().getStatus() == Status.STOPPED) {
            machineService.startMachine(id, false);
            return ResponseEntity.ok(optionalMachine.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping(value = "/stop/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> stopMachine(@PathVariable Long id) throws InterruptedException {

        Optional<Machine> optionalMachine = machineService.findById(id);

        if(optionalMachine.isPresent() && optionalMachine.get().getStatus() == Status.RUNNING) {
            machineService.stopMachine(id, false);
            return ResponseEntity.ok(optionalMachine.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping(value = "/restart/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> restartMachine(@PathVariable Long id) throws InterruptedException, ParseException {

        Optional<Machine> optionalMachine = machineService.findById(id);

        if(optionalMachine.isPresent() && optionalMachine.get().getStatus() == Status.RUNNING) {
            machineService.restartMachine(id, false);
            return ResponseEntity.ok(optionalMachine.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping(value = "/schedule")
    public ResponseEntity<?> scheduleMachine(@RequestBody ScheduleRequest scheduleRequest) throws ParseException {
        machineService.scheduleMachine(scheduleRequest.getId(),scheduleRequest.getDate(),scheduleRequest.getTime(),scheduleRequest.getAction());
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping(value = "/errors", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getErrorHistory(@PathParam("mail") String mail){
        return ResponseEntity.ok(machineService.findAllErrorsForUser(mail));
    }

}