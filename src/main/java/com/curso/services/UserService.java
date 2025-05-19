package com.curso.services;

import com.curso.domains.User;
import com.curso.domains.dtos.UserDTO;
import com.curso.repositories.UserRepository;
import com.curso.services.exceptions.DataIntegrityViolationException;
import com.curso.services.exceptions.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService {

    @Autowired
    private UserRepository usersRepo;

    public List<UserDTO> findAll() {
        //retorna uma lista de UserDTO
        return usersRepo.findAll().stream().map(obj -> new UserDTO(obj)).collect(Collectors.toList());
    }


    public User findById(Long id) {
        Optional<User> obj = usersRepo.findById(id);
        return obj.orElseThrow(() -> new ObjectNotFoundException("Objeto não encontrado! id:" + id));
    }

    public User findByCpf(String cpf) {
        Optional<User> obj = usersRepo.findByCpf(cpf);
        return obj.orElseThrow(() -> new ObjectNotFoundException("Objeto não encontrado! cpf:" + cpf));
    }

    public User findByEmail(String email) {
        Optional<User> obj = usersRepo.findByEmail(email);
        return obj.orElseThrow(() -> new ObjectNotFoundException("Objeto não encontrado! email:" + email));
    }

    public User create(UserDTO objDto) {
        objDto.setId(null);
        ValidaPorCPFeEmail(objDto);
        User newObj = new User(objDto);
        return usersRepo.save(newObj);
    }

    public User update(Long id, UserDTO objDTO) {
        objDTO.setId(id);
        User oldObj = findById(id);
        ValidaPorCPFeEmail(objDTO);
        oldObj = new User(objDTO);
        return usersRepo.save(oldObj);
    }

    public void delete(Long id) {
        User obj = findById(id);
        if (obj.getServiceOrders().size() > 0) {
            throw new DataIntegrityViolationException("Usuário não pode ser deletado pois possui ordens de serviço!");
        }
        usersRepo.deleteById(id);
    }

    public void ValidaPorCPFeEmail(UserDTO objDto) {
        Optional<User> obj = usersRepo.findByCpf(objDto.getCpf());
        if (obj.isPresent() && obj.get().getId() != objDto.getId()) {
            throw new DataIntegrityViolationException("CPF já cadastrado no sistema");
        }
        obj = usersRepo.findByEmail(objDto.getEmail());
        if (obj.isPresent() && obj.get().getId() != objDto.getId()) {
            throw new DataIntegrityViolationException("Email já cadastrado no sistema");
        }
    }
}
