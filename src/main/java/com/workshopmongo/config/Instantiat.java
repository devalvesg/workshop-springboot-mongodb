package com.workshopmongo.config;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.TimeZone;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;

import com.workshopmongo.domain.Post;
import com.workshopmongo.domain.User;
import com.workshopmongo.dto.AuthorDTO;
import com.workshopmongo.dto.CommentDTO;
import com.workshopmongo.repository.PostRepository;
import com.workshopmongo.repository.UserRepository;

@Configuration
public class Instantiat implements CommandLineRunner{

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private PostRepository postRepository;
	
	@Override
	public void run(String... args) throws Exception {
		
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		sdf.setTimeZone(TimeZone.getTimeZone("GMT"));
		userRepository.deleteAll();
		postRepository.deleteAll();
		
		User maria = new User(null, "Maria Brown", "maria@gmail.com");
		User alex = new User(null, "Alex Green", "alex@gmail.com");
		User bob = new User(null, "Bob Grey", "bob@gmail.com");

		userRepository.saveAll(Arrays.asList(maria, alex, bob));
		
		Post p1 = new Post(null,sdf.parse("21/03/2018") , "Partiu viagem!", "Vou viajar para SP, abraços!", new AuthorDTO(maria));
		Post p2 = new Post(null,sdf.parse("23/03/2018") , "Bom dia", "Acordei feliz hoje!", new AuthorDTO(maria));
		
		CommentDTO com1 = new CommentDTO("Boa viagem mano",sdf.parse("21/03/2018") , new AuthorDTO(alex));
		CommentDTO com2 = new CommentDTO("Aproveite!",sdf.parse("22/03/2018") , new AuthorDTO(bob));
		CommentDTO com3 = new CommentDTO("Tenha um otimo dia!",sdf.parse("23/03/2018") , new AuthorDTO(alex));
				
		p1.getComents().addAll(Arrays.asList(com1, com2));
		p2.getComents().addAll(Arrays.asList(com3));
		
		postRepository.saveAll(Arrays.asList(p1, p2));
		
		maria.getPosts().addAll(Arrays.asList(p1, p2));
		userRepository.save(maria);
		
	}

}
