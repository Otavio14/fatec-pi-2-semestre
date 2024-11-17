export const ProfilePedidos = ({ show, setShow }) => {

    return(
        <div className={`${!!show ? "flex" : "hidden"}`}>
            Meus pedidos
        </div>
    )
}