
package com.user.model;

import java.util.ArrayList;
import java.util.List;

import jakarta.annotation.Generated;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name="micro_user")

public class user_model {
	@Id
		private String userId;
		private String name;
		private String email;
		private String about;
		@Transient //database madhi store karnar nahi
        private List<Rating> ratings=new ArrayList<>();
}
