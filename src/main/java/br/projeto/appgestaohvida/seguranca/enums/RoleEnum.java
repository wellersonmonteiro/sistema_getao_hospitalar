package br.projeto.appgestaohvida.seguranca.enums;

import lombok.Getter;



@Getter
public enum RoleEnum {
   ADMIN("ROLE_ADMIN"), USER("ROLE_USUARIO");
   private String role;
   private RoleEnum(String role) {this.role = role;}

   @Override
   public String toString() {
       return this.role;
   }
}