<customer>
  <id>{data(/all/customer/@custNo)}</id>
  <orders>
    { for $order
      in  /all/orders/order
      return <order><id>{data($order/@id)}</id><product name='{data($order/@product)}'/></order>
    }
  </orders>
</customer>