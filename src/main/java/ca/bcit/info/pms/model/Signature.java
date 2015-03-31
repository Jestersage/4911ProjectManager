package ca.bcit.info.pms.model;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;

/**
 * Timesheet electronic signature.
 */
@Entity
public class Signature implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "signId", updatable = false, nullable = false)
    private int id;

    private byte[] signature;

    @Lob
	@Column(name = "publicKey", nullable = false, columnDefinition = "blob")
    private byte[] publicKey;
    
    public Signature() {}
    
    public Signature(byte[] sig, byte[] pubKey)
	{
		signature = sig;
		publicKey = pubKey;
	} 

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public byte[] getSignature() {
        return signature;
    }

    public void setSignature(byte[] signature) {
        this.signature = signature;
    }

    public byte[] getPublicKey() {
        return publicKey;
    }

    public void setPublicKey(byte[] publicKey) {
        this.publicKey = publicKey;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Signature)) return false;

        Signature signature = (Signature) o;

        if (id != signature.id) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return id;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("Signature{");
        sb.append("id=").append(id);
        sb.append(", signature=");
        if (signature == null) sb.append("null");
        else {
            sb.append('[');
            for (int i = 0; i < signature.length; ++i)
                sb.append(i == 0 ? "" : ", ").append(signature[i]);
            sb.append(']');
        }
        sb.append(", publicKey=");
        if (publicKey == null) sb.append("null");
        else {
            sb.append('[');
            for (int i = 0; i < publicKey.length; ++i)
                sb.append(i == 0 ? "" : ", ").append(publicKey[i]);
            sb.append(']');
        }
        sb.append('}');
        return sb.toString();
    }
}
