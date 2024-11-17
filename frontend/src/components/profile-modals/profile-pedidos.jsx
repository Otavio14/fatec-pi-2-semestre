export const ProfilePedidos = ({ show, setShow, pedidos }) => {

    return (
        <div className={`${!!show ? "shadow-md bg-gray-100 p-6 rounded-lg" : "hidden"}`}>
            <h2 className="text-2xl font-bold mb-4">Meus Pedidos</h2>
            {pedidos.length >= 1 ? (
                <ul>
                    <li className="mb-2">Pedido #12345 - Status: Entregue</li>
                    <li className="mb-2">Pedido #12346 - Status: Em trânsito</li>
                    <li className="mb-2">Pedido #12347 - Status: Processando</li>
                </ul>
            ) : (<p>Você não fez nenhum pedido</p>)}
        </div>
    )
}