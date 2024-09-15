package com.example.dat250ass3.security

import com.example.dat250ass3.model.domain.UserWithoutPassword
import org.springframework.security.authentication.AbstractAuthenticationToken
import org.springframework.security.core.GrantedAuthority

class SessionAuthenticationToken(
    private val user: UserWithoutPassword,
    authorities: Collection<GrantedAuthority> = emptyList(),
) : AbstractAuthenticationToken(authorities) {
    init {
        isAuthenticated = true
    }

    override fun getCredentials(): Any? = null

    override fun getPrincipal(): Any = user
}
