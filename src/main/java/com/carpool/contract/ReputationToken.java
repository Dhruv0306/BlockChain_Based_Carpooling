package com.carpool.contract;

import io.reactivex.Flowable;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import org.web3j.abi.EventEncoder;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.Address;
import org.web3j.abi.datatypes.Bool;
import org.web3j.abi.datatypes.Event;
import org.web3j.abi.datatypes.Function;
import org.web3j.abi.datatypes.Type;
import org.web3j.abi.datatypes.Utf8String;
import org.web3j.abi.datatypes.generated.Uint256;
import org.web3j.abi.datatypes.generated.Uint8;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameter;
import org.web3j.protocol.core.RemoteCall;
import org.web3j.protocol.core.RemoteFunctionCall;
import org.web3j.protocol.core.methods.request.EthFilter;
import org.web3j.protocol.core.methods.response.BaseEventResponse;
import org.web3j.protocol.core.methods.response.Log;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.tx.Contract;
import org.web3j.tx.TransactionManager;
import org.web3j.tx.gas.ContractGasProvider;

/**
 * <p>Auto generated code.
 * <p><strong>Do not modify!</strong>
 * <p>Please use the <a href="https://docs.web3j.io/command_line.html">web3j command line tools</a>,
 * or the org.web3j.codegen.SolidityFunctionWrapperGenerator in the 
 * <a href="https://github.com/hyperledger-web3j/web3j/tree/main/codegen">codegen module</a> to update.
 *
 * <p>Generated with web3j version 1.6.3.
 */
@SuppressWarnings("rawtypes")
public class ReputationToken extends Contract {
    public static final String BINARY = "0x60806040523480156200001157600080fd5b506040518060400160405280601181526020017f436172706f6f6c52657075746174696f6e0000000000000000000000000000008152506040518060400160405280600481526020017f4350545200000000000000000000000000000000000000000000000000000000815250816003908051906020019062000096929190620001a6565b508060049080519060200190620000af929190620001a6565b505050620000d2620000c6620000d860201b60201c565b620000e060201b60201c565b620002bb565b600033905090565b6000600560009054906101000a900473ffffffffffffffffffffffffffffffffffffffff16905081600560006101000a81548173ffffffffffffffffffffffffffffffffffffffff021916908373ffffffffffffffffffffffffffffffffffffffff1602179055508173ffffffffffffffffffffffffffffffffffffffff168173ffffffffffffffffffffffffffffffffffffffff167f8be0079c531659141344cd1fd0a4f28419497f9722a3daafe3b4186f6b6457e060405160405180910390a35050565b828054620001b49062000256565b90600052602060002090601f016020900481019282620001d8576000855562000224565b82601f10620001f357805160ff191683800117855562000224565b8280016001018555821562000224579182015b828111156200022357825182559160200191906001019062000206565b5b50905062000233919062000237565b5090565b5b808211156200025257600081600090555060010162000238565b5090565b600060028204905060018216806200026f57607f821691505b602082108114156200028657620002856200028c565b5b50919050565b7f4e487b7100000000000000000000000000000000000000000000000000000000600052602260045260246000fd5b61203180620002cb6000396000f3fe608060405234801561001057600080fd5b50600436106101165760003560e01c806375a67d5c116100a2578063a9059cbb11610071578063a9059cbb1461030b578063dd62ed3e1461033b578063e023c1801461036b578063f2fde38b1461039b578063ffe5dcf8146103b757610116565b806375a67d5c1461026f5780638da5cb5b1461029f57806395d89b41146102bd578063a457c2d7146102db57610116565b806323b872dd116100e957806323b872dd146101b7578063313ce567146101e7578063395093511461020557806370a0823114610235578063715018a61461026557610116565b8063056eb1bf1461011b57806306fdde031461014b578063095ea7b31461016957806318160ddd14610199575b600080fd5b6101356004803603810190610130919061136c565b6103d3565b6040516101429190611bc4565b60405180910390f35b6101536103eb565b6040516101609190611a02565b60405180910390f35b610183600480360381019061017e919061145c565b61047d565b60405161019091906119e7565b60405180910390f35b6101a16104a0565b6040516101ae9190611bc4565b60405180910390f35b6101d160048036038101906101cc91906113d1565b6104aa565b6040516101de91906119e7565b60405180910390f35b6101ef6104d9565b6040516101fc9190611bdf565b60405180910390f35b61021f600480360381019061021a919061145c565b6104e2565b60405161022c91906119e7565b60405180910390f35b61024f600480360381019061024a919061136c565b610519565b60405161025c9190611bc4565b60405180910390f35b61026d610561565b005b6102896004803603810190610284919061136c565b610575565b6040516102969190611bc4565b60405180910390f35b6102a7610587565b6040516102b491906119a3565b60405180910390f35b6102c56105b1565b6040516102d29190611a02565b60405180910390f35b6102f560048036038101906102f0919061145c565b610643565b60405161030291906119e7565b60405180910390f35b6103256004803603810190610320919061145c565b6106ba565b60405161033291906119e7565b60405180910390f35b61035560048036038101906103509190611395565b6106dd565b6040516103629190611bc4565b60405180910390f35b6103856004803603810190610380919061136c565b610764565b6040516103929190611bc4565b60405180910390f35b6103b560048036038101906103b0919061136c565b61077c565b005b6103d160048036038101906103cc9190611420565b610800565b005b60076020528060005260406000206000915090505481565b6060600380546103fa90611ebf565b80601f016020809104026020016040519081016040528092919081815260200182805461042690611ebf565b80156104735780601f1061044857610100808354040283529160200191610473565b820191906000526020600020905b81548152906001019060200180831161045657829003601f168201915b5050505050905090565b6000806104886109e3565b90506104958185856109eb565b600191505092915050565b6000600254905090565b6000806104b56109e3565b90506104c2858285610bb6565b6104cd858585610c42565b60019150509392505050565b60006012905090565b6000806104ed6109e3565b905061050e8185856104ff85896106dd565b6105099190611c16565b6109eb565b600191505092915050565b60008060008373ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff168152602001908152602001600020549050919050565b610569610eba565b6105736000610f38565b565b600061058082610519565b9050919050565b6000600560009054906101000a900473ffffffffffffffffffffffffffffffffffffffff16905090565b6060600480546105c090611ebf565b80601f01602080910402602001604051908101604052809291908181526020018280546105ec90611ebf565b80156106395780601f1061060e57610100808354040283529160200191610639565b820191906000526020600020905b81548152906001019060200180831161061c57829003601f168201915b5050505050905090565b60008061064e6109e3565b9050600061065c82866106dd565b9050838110156106a1576040517f08c379a000000000000000000000000000000000000000000000000000000000815260040161069890611b84565b60405180910390fd5b6106ae82868684036109eb565b60019250505092915050565b6000806106c56109e3565b90506106d2818585610c42565b600191505092915050565b6000600160008473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200190815260200160002060008373ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200190815260200160002054905092915050565b60066020528060005260406000206000915090505481565b610784610eba565b600073ffffffffffffffffffffffffffffffffffffffff168173ffffffffffffffffffffffffffffffffffffffff1614156107f4576040517f08c379a00000000000000000000000000000000000000000000000000000000081526004016107eb90611a64565b60405180910390fd5b6107fd81610f38565b50565b3373ffffffffffffffffffffffffffffffffffffffff168273ffffffffffffffffffffffffffffffffffffffff16141561086f576040517f08c379a000000000000000000000000000000000000000000000000000000000815260040161086690611b64565b60405180910390fd5b80156108f857600660008373ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200190815260200160002060008154809291906108c590611ef1565b91905055506108f3826108d66104d9565b600a6108e29190611cbf565b60016108ee9190611ddd565b610ffe565b6109a6565b600760008373ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff168152602001908152602001600020600081548092919061094890611ef1565b91905055506109556104d9565b600a6109619190611cbf565b600161096d9190611ddd565b61097683610519565b106109a5576109a4826109876104d9565b600a6109939190611cbf565b600161099f9190611ddd565b611155565b5b5b7ff1fb3b6b6f4e74cfebdd242c4d0538558331f7b3b5f7e01a3220c9fcd3903a2282826040516109d79291906119be565b60405180910390a15050565b600033905090565b600073ffffffffffffffffffffffffffffffffffffffff168373ffffffffffffffffffffffffffffffffffffffff161415610a5b576040517f08c379a0000000000000000000000000000000000000000000000000000000008152600401610a5290611b44565b60405180910390fd5b600073ffffffffffffffffffffffffffffffffffffffff168273ffffffffffffffffffffffffffffffffffffffff161415610acb576040517f08c379a0000000000000000000000000000000000000000000000000000000008152600401610ac290611a84565b60405180910390fd5b80600160008573ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200190815260200160002060008473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff168152602001908152602001600020819055508173ffffffffffffffffffffffffffffffffffffffff168373ffffffffffffffffffffffffffffffffffffffff167f8c5be1e5ebec7d5bd14f71427d1e84f3dd0314c0f7b2291e5b200ac8c7c3b92583604051610ba99190611bc4565b60405180910390a3505050565b6000610bc284846106dd565b90507fffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffff8114610c3c5781811015610c2e576040517f08c379a0000000000000000000000000000000000000000000000000000000008152600401610c2590611aa4565b60405180910390fd5b610c3b84848484036109eb565b5b50505050565b600073ffffffffffffffffffffffffffffffffffffffff168373ffffffffffffffffffffffffffffffffffffffff161415610cb2576040517f08c379a0000000000000000000000000000000000000000000000000000000008152600401610ca990611b24565b60405180910390fd5b600073ffffffffffffffffffffffffffffffffffffffff168273ffffffffffffffffffffffffffffffffffffffff161415610d22576040517f08c379a0000000000000000000000000000000000000000000000000000000008152600401610d1990611a24565b60405180910390fd5b610d2d838383611323565b60008060008573ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200190815260200160002054905081811015610db3576040517f08c379a0000000000000000000000000000000000000000000000000000000008152600401610daa90611ac4565b60405180910390fd5b8181036000808673ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200190815260200160002081905550816000808573ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff168152602001908152602001600020600082825401925050819055508273ffffffffffffffffffffffffffffffffffffffff168473ffffffffffffffffffffffffffffffffffffffff167fddf252ad1be2c89b69c2b068fc378daa952ba7f163c4a11628f55a4df523b3ef84604051610ea19190611bc4565b60405180910390a3610eb4848484611328565b50505050565b610ec26109e3565b73ffffffffffffffffffffffffffffffffffffffff16610ee0610587565b73ffffffffffffffffffffffffffffffffffffffff1614610f36576040517f08c379a0000000000000000000000000000000000000000000000000000000008152600401610f2d90611ae4565b60405180910390fd5b565b6000600560009054906101000a900473ffffffffffffffffffffffffffffffffffffffff16905081600560006101000a81548173ffffffffffffffffffffffffffffffffffffffff021916908373ffffffffffffffffffffffffffffffffffffffff1602179055508173ffffffffffffffffffffffffffffffffffffffff168173ffffffffffffffffffffffffffffffffffffffff167f8be0079c531659141344cd1fd0a4f28419497f9722a3daafe3b4186f6b6457e060405160405180910390a35050565b600073ffffffffffffffffffffffffffffffffffffffff168273ffffffffffffffffffffffffffffffffffffffff16141561106e576040517f08c379a000000000000000000000000000000000000000000000000000000000815260040161106590611ba4565b60405180910390fd5b61107a60008383611323565b806002600082825461108c9190611c16565b92505081905550806000808473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff168152602001908152602001600020600082825401925050819055508173ffffffffffffffffffffffffffffffffffffffff16600073ffffffffffffffffffffffffffffffffffffffff167fddf252ad1be2c89b69c2b068fc378daa952ba7f163c4a11628f55a4df523b3ef8360405161113d9190611bc4565b60405180910390a361115160008383611328565b5050565b600073ffffffffffffffffffffffffffffffffffffffff168273ffffffffffffffffffffffffffffffffffffffff1614156111c5576040517f08c379a00000000000000000000000000000000000000000000000000000000081526004016111bc90611b04565b60405180910390fd5b6111d182600083611323565b60008060008473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200190815260200160002054905081811015611257576040517f08c379a000000000000000000000000000000000000000000000000000000000815260040161124e90611a44565b60405180910390fd5b8181036000808573ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1681526020019081526020016000208190555081600260008282540392505081905550600073ffffffffffffffffffffffffffffffffffffffff168373ffffffffffffffffffffffffffffffffffffffff167fddf252ad1be2c89b69c2b068fc378daa952ba7f163c4a11628f55a4df523b3ef8460405161130a9190611bc4565b60405180910390a361131e83600084611328565b505050565b505050565b505050565b60008135905061133c81611fb6565b92915050565b60008135905061135181611fcd565b92915050565b60008135905061136681611fe4565b92915050565b60006020828403121561137e57600080fd5b600061138c8482850161132d565b91505092915050565b600080604083850312156113a857600080fd5b60006113b68582860161132d565b92505060206113c78582860161132d565b9150509250929050565b6000806000606084860312156113e657600080fd5b60006113f48682870161132d565b93505060206114058682870161132d565b925050604061141686828701611357565b9150509250925092565b6000806040838503121561143357600080fd5b60006114418582860161132d565b925050602061145285828601611342565b9150509250929050565b6000806040838503121561146f57600080fd5b600061147d8582860161132d565b925050602061148e85828601611357565b9150509250929050565b6114a181611e37565b82525050565b6114b081611e49565b82525050565b60006114c182611bfa565b6114cb8185611c05565b93506114db818560208601611e8c565b6114e481611f98565b840191505092915050565b60006114fc602383611c05565b91507f45524332303a207472616e7366657220746f20746865207a65726f206164647260008301527f65737300000000000000000000000000000000000000000000000000000000006020830152604082019050919050565b6000611562602283611c05565b91507f45524332303a206275726e20616d6f756e7420657863656564732062616c616e60008301527f63650000000000000000000000000000000000000000000000000000000000006020830152604082019050919050565b60006115c8602683611c05565b91507f4f776e61626c653a206e6577206f776e657220697320746865207a65726f206160008301527f64647265737300000000000000000000000000000000000000000000000000006020830152604082019050919050565b600061162e602283611c05565b91507f45524332303a20617070726f766520746f20746865207a65726f20616464726560008301527f73730000000000000000000000000000000000000000000000000000000000006020830152604082019050919050565b6000611694601d83611c05565b91507f45524332303a20696e73756666696369656e7420616c6c6f77616e63650000006000830152602082019050919050565b60006116d4602683611c05565b91507f45524332303a207472616e7366657220616d6f756e742065786365656473206260008301527f616c616e636500000000000000000000000000000000000000000000000000006020830152604082019050919050565b600061173a602083611c05565b91507f4f776e61626c653a2063616c6c6572206973206e6f7420746865206f776e65726000830152602082019050919050565b600061177a602183611c05565b91507f45524332303a206275726e2066726f6d20746865207a65726f2061646472657360008301527f73000000000000000000000000000000000000000000000000000000000000006020830152604082019050919050565b60006117e0602583611c05565b91507f45524332303a207472616e736665722066726f6d20746865207a65726f20616460008301527f64726573730000000000000000000000000000000000000000000000000000006020830152604082019050919050565b6000611846602483611c05565b91507f45524332303a20617070726f76652066726f6d20746865207a65726f2061646460008301527f72657373000000000000000000000000000000000000000000000000000000006020830152604082019050919050565b60006118ac601483611c05565b91507f43616e6e6f74207261746520796f757273656c660000000000000000000000006000830152602082019050919050565b60006118ec602583611c05565b91507f45524332303a2064656372656173656420616c6c6f77616e63652062656c6f7760008301527f207a65726f0000000000000000000000000000000000000000000000000000006020830152604082019050919050565b6000611952601f83611c05565b91507f45524332303a206d696e7420746f20746865207a65726f2061646472657373006000830152602082019050919050565b61198e81611e75565b82525050565b61199d81611e7f565b82525050565b60006020820190506119b86000830184611498565b92915050565b60006040820190506119d36000830185611498565b6119e060208301846114a7565b9392505050565b60006020820190506119fc60008301846114a7565b92915050565b60006020820190508181036000830152611a1c81846114b6565b905092915050565b60006020820190508181036000830152611a3d816114ef565b9050919050565b60006020820190508181036000830152611a5d81611555565b9050919050565b60006020820190508181036000830152611a7d816115bb565b9050919050565b60006020820190508181036000830152611a9d81611621565b9050919050565b60006020820190508181036000830152611abd81611687565b9050919050565b60006020820190508181036000830152611add816116c7565b9050919050565b60006020820190508181036000830152611afd8161172d565b9050919050565b60006020820190508181036000830152611b1d8161176d565b9050919050565b60006020820190508181036000830152611b3d816117d3565b9050919050565b60006020820190508181036000830152611b5d81611839565b9050919050565b60006020820190508181036000830152611b7d8161189f565b9050919050565b60006020820190508181036000830152611b9d816118df565b9050919050565b60006020820190508181036000830152611bbd81611945565b9050919050565b6000602082019050611bd96000830184611985565b92915050565b6000602082019050611bf46000830184611994565b92915050565b600081519050919050565b600082825260208201905092915050565b6000611c2182611e75565b9150611c2c83611e75565b9250827fffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffff03821115611c6157611c60611f3a565b5b828201905092915050565b6000808291508390505b6001851115611cb657808604811115611c9257611c91611f3a565b5b6001851615611ca15780820291505b8081029050611caf85611fa9565b9450611c76565b94509492505050565b6000611cca82611e75565b9150611cd583611e7f565b9250611d027fffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffff8484611d0a565b905092915050565b600082611d1a5760019050611dd6565b81611d285760009050611dd6565b8160018114611d3e5760028114611d4857611d77565b6001915050611dd6565b60ff841115611d5a57611d59611f3a565b5b8360020a915084821115611d7157611d70611f3a565b5b50611dd6565b5060208310610133831016604e8410600b8410161715611dac5782820a905083811115611da757611da6611f3a565b5b611dd6565b611db98484846001611c6c565b92509050818404811115611dd057611dcf611f3a565b5b81810290505b9392505050565b6000611de882611e75565b9150611df383611e75565b9250817fffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffff0483118215151615611e2c57611e2b611f3a565b5b828202905092915050565b6000611e4282611e55565b9050919050565b60008115159050919050565b600073ffffffffffffffffffffffffffffffffffffffff82169050919050565b6000819050919050565b600060ff82169050919050565b60005b83811015611eaa578082015181840152602081019050611e8f565b83811115611eb9576000848401525b50505050565b60006002820490506001821680611ed757607f821691505b60208210811415611eeb57611eea611f69565b5b50919050565b6000611efc82611e75565b91507fffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffff821415611f2f57611f2e611f3a565b5b600182019050919050565b7f4e487b7100000000000000000000000000000000000000000000000000000000600052601160045260246000fd5b7f4e487b7100000000000000000000000000000000000000000000000000000000600052602260045260246000fd5b6000601f19601f8301169050919050565b60008160011c9050919050565b611fbf81611e37565b8114611fca57600080fd5b50565b611fd681611e49565b8114611fe157600080fd5b50565b611fed81611e75565b8114611ff857600080fd5b5056fea26469706673582212202db6cea412da134c9d98a2b546bc2071bfd2684f0b1c7b6944f723961b36c24264736f6c63430008000033";

    private static String librariesLinkedBinary;

    public static final String FUNC_ALLOWANCE = "allowance";

    public static final String FUNC_APPROVE = "approve";

    public static final String FUNC_BALANCEOF = "balanceOf";

    public static final String FUNC_DECIMALS = "decimals";

    public static final String FUNC_DECREASEALLOWANCE = "decreaseAllowance";

    public static final String FUNC_INCREASEALLOWANCE = "increaseAllowance";

    public static final String FUNC_NAME = "name";

    public static final String FUNC_NEGATIVERATINGS = "negativeRatings";

    public static final String FUNC_OWNER = "owner";

    public static final String FUNC_POSITIVERATINGS = "positiveRatings";

    public static final String FUNC_RENOUNCEOWNERSHIP = "renounceOwnership";

    public static final String FUNC_SYMBOL = "symbol";

    public static final String FUNC_TOTALSUPPLY = "totalSupply";

    public static final String FUNC_TRANSFER = "transfer";

    public static final String FUNC_TRANSFERFROM = "transferFrom";

    public static final String FUNC_TRANSFEROWNERSHIP = "transferOwnership";

    public static final String FUNC_RATEUSER = "rateUser";

    public static final String FUNC_GETREPUTATIONSCORE = "getReputationScore";

    public static final Event APPROVAL_EVENT = new Event("Approval", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Address>(true) {}, new TypeReference<Address>(true) {}, new TypeReference<Uint256>() {}));
    ;

    public static final Event OWNERSHIPTRANSFERRED_EVENT = new Event("OwnershipTransferred", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Address>(true) {}, new TypeReference<Address>(true) {}));
    ;

    public static final Event TRANSFER_EVENT = new Event("Transfer", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Address>(true) {}, new TypeReference<Address>(true) {}, new TypeReference<Uint256>() {}));
    ;

    public static final Event USERRATED_EVENT = new Event("UserRated", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}, new TypeReference<Bool>() {}));
    ;

    protected static final HashMap<String, String> _addresses;

    static {
        _addresses = new HashMap<String, String>();
        _addresses.put("137", "0x71553F816F579eeEf33Eb223376a456Ab47Dd33c");
    }

    @Deprecated
    protected ReputationToken(String contractAddress, Web3j web3j, Credentials credentials,
            BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    protected ReputationToken(String contractAddress, Web3j web3j, Credentials credentials,
            ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, credentials, contractGasProvider);
    }

    @Deprecated
    protected ReputationToken(String contractAddress, Web3j web3j,
            TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    protected ReputationToken(String contractAddress, Web3j web3j,
            TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public static List<ApprovalEventResponse> getApprovalEvents(
            TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = staticExtractEventParametersWithLog(APPROVAL_EVENT, transactionReceipt);
        ArrayList<ApprovalEventResponse> responses = new ArrayList<ApprovalEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            ApprovalEventResponse typedResponse = new ApprovalEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.owner = (String) eventValues.getIndexedValues().get(0).getValue();
            typedResponse.spender = (String) eventValues.getIndexedValues().get(1).getValue();
            typedResponse.value = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public static ApprovalEventResponse getApprovalEventFromLog(Log log) {
        Contract.EventValuesWithLog eventValues = staticExtractEventParametersWithLog(APPROVAL_EVENT, log);
        ApprovalEventResponse typedResponse = new ApprovalEventResponse();
        typedResponse.log = log;
        typedResponse.owner = (String) eventValues.getIndexedValues().get(0).getValue();
        typedResponse.spender = (String) eventValues.getIndexedValues().get(1).getValue();
        typedResponse.value = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
        return typedResponse;
    }

    public Flowable<ApprovalEventResponse> approvalEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(log -> getApprovalEventFromLog(log));
    }

    public Flowable<ApprovalEventResponse> approvalEventFlowable(DefaultBlockParameter startBlock,
            DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(APPROVAL_EVENT));
        return approvalEventFlowable(filter);
    }

    public static List<OwnershipTransferredEventResponse> getOwnershipTransferredEvents(
            TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = staticExtractEventParametersWithLog(OWNERSHIPTRANSFERRED_EVENT, transactionReceipt);
        ArrayList<OwnershipTransferredEventResponse> responses = new ArrayList<OwnershipTransferredEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            OwnershipTransferredEventResponse typedResponse = new OwnershipTransferredEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.previousOwner = (String) eventValues.getIndexedValues().get(0).getValue();
            typedResponse.newOwner = (String) eventValues.getIndexedValues().get(1).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public static OwnershipTransferredEventResponse getOwnershipTransferredEventFromLog(Log log) {
        Contract.EventValuesWithLog eventValues = staticExtractEventParametersWithLog(OWNERSHIPTRANSFERRED_EVENT, log);
        OwnershipTransferredEventResponse typedResponse = new OwnershipTransferredEventResponse();
        typedResponse.log = log;
        typedResponse.previousOwner = (String) eventValues.getIndexedValues().get(0).getValue();
        typedResponse.newOwner = (String) eventValues.getIndexedValues().get(1).getValue();
        return typedResponse;
    }

    public Flowable<OwnershipTransferredEventResponse> ownershipTransferredEventFlowable(
            EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(log -> getOwnershipTransferredEventFromLog(log));
    }

    public Flowable<OwnershipTransferredEventResponse> ownershipTransferredEventFlowable(
            DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(OWNERSHIPTRANSFERRED_EVENT));
        return ownershipTransferredEventFlowable(filter);
    }

    public static List<TransferEventResponse> getTransferEvents(
            TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = staticExtractEventParametersWithLog(TRANSFER_EVENT, transactionReceipt);
        ArrayList<TransferEventResponse> responses = new ArrayList<TransferEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            TransferEventResponse typedResponse = new TransferEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.from = (String) eventValues.getIndexedValues().get(0).getValue();
            typedResponse.to = (String) eventValues.getIndexedValues().get(1).getValue();
            typedResponse.value = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public static TransferEventResponse getTransferEventFromLog(Log log) {
        Contract.EventValuesWithLog eventValues = staticExtractEventParametersWithLog(TRANSFER_EVENT, log);
        TransferEventResponse typedResponse = new TransferEventResponse();
        typedResponse.log = log;
        typedResponse.from = (String) eventValues.getIndexedValues().get(0).getValue();
        typedResponse.to = (String) eventValues.getIndexedValues().get(1).getValue();
        typedResponse.value = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
        return typedResponse;
    }

    public Flowable<TransferEventResponse> transferEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(log -> getTransferEventFromLog(log));
    }

    public Flowable<TransferEventResponse> transferEventFlowable(DefaultBlockParameter startBlock,
            DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(TRANSFER_EVENT));
        return transferEventFlowable(filter);
    }

    public static List<UserRatedEventResponse> getUserRatedEvents(
            TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = staticExtractEventParametersWithLog(USERRATED_EVENT, transactionReceipt);
        ArrayList<UserRatedEventResponse> responses = new ArrayList<UserRatedEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            UserRatedEventResponse typedResponse = new UserRatedEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.user = (String) eventValues.getNonIndexedValues().get(0).getValue();
            typedResponse.isPositive = (Boolean) eventValues.getNonIndexedValues().get(1).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public static UserRatedEventResponse getUserRatedEventFromLog(Log log) {
        Contract.EventValuesWithLog eventValues = staticExtractEventParametersWithLog(USERRATED_EVENT, log);
        UserRatedEventResponse typedResponse = new UserRatedEventResponse();
        typedResponse.log = log;
        typedResponse.user = (String) eventValues.getNonIndexedValues().get(0).getValue();
        typedResponse.isPositive = (Boolean) eventValues.getNonIndexedValues().get(1).getValue();
        return typedResponse;
    }

    public Flowable<UserRatedEventResponse> userRatedEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(log -> getUserRatedEventFromLog(log));
    }

    public Flowable<UserRatedEventResponse> userRatedEventFlowable(DefaultBlockParameter startBlock,
            DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(USERRATED_EVENT));
        return userRatedEventFlowable(filter);
    }

    public RemoteFunctionCall<BigInteger> allowance(String owner, String spender) {
        final Function function = new Function(FUNC_ALLOWANCE, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(owner), 
                new org.web3j.abi.datatypes.Address(spender)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteFunctionCall<TransactionReceipt> approve(String spender, BigInteger amount) {
        final Function function = new Function(
                FUNC_APPROVE, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(spender), 
                new org.web3j.abi.datatypes.generated.Uint256(amount)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<BigInteger> balanceOf(String account) {
        final Function function = new Function(FUNC_BALANCEOF, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(account)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteFunctionCall<BigInteger> decimals() {
        final Function function = new Function(FUNC_DECIMALS, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint8>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteFunctionCall<TransactionReceipt> decreaseAllowance(String spender,
            BigInteger subtractedValue) {
        final Function function = new Function(
                FUNC_DECREASEALLOWANCE, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(spender), 
                new org.web3j.abi.datatypes.generated.Uint256(subtractedValue)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> increaseAllowance(String spender,
            BigInteger addedValue) {
        final Function function = new Function(
                FUNC_INCREASEALLOWANCE, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(spender), 
                new org.web3j.abi.datatypes.generated.Uint256(addedValue)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<String> name() {
        final Function function = new Function(FUNC_NAME, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteFunctionCall<BigInteger> negativeRatings(String param0) {
        final Function function = new Function(FUNC_NEGATIVERATINGS, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(param0)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteFunctionCall<String> owner() {
        final Function function = new Function(FUNC_OWNER, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteFunctionCall<BigInteger> positiveRatings(String param0) {
        final Function function = new Function(FUNC_POSITIVERATINGS, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(param0)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteFunctionCall<TransactionReceipt> renounceOwnership() {
        final Function function = new Function(
                FUNC_RENOUNCEOWNERSHIP, 
                Arrays.<Type>asList(), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<String> symbol() {
        final Function function = new Function(FUNC_SYMBOL, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteFunctionCall<BigInteger> totalSupply() {
        final Function function = new Function(FUNC_TOTALSUPPLY, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteFunctionCall<TransactionReceipt> transfer(String to, BigInteger amount) {
        final Function function = new Function(
                FUNC_TRANSFER, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(to), 
                new org.web3j.abi.datatypes.generated.Uint256(amount)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> transferFrom(String from, String to,
            BigInteger amount) {
        final Function function = new Function(
                FUNC_TRANSFERFROM, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(from), 
                new org.web3j.abi.datatypes.Address(to), 
                new org.web3j.abi.datatypes.generated.Uint256(amount)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> transferOwnership(String newOwner) {
        final Function function = new Function(
                FUNC_TRANSFEROWNERSHIP, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(newOwner)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> rateUser(String user, Boolean isPositive) {
        final Function function = new Function(
                FUNC_RATEUSER, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(user), 
                new org.web3j.abi.datatypes.Bool(isPositive)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<BigInteger> getReputationScore(String user) {
        final Function function = new Function(FUNC_GETREPUTATIONSCORE, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(user)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    @Deprecated
    public static ReputationToken load(String contractAddress, Web3j web3j, Credentials credentials,
            BigInteger gasPrice, BigInteger gasLimit) {
        return new ReputationToken(contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    @Deprecated
    public static ReputationToken load(String contractAddress, Web3j web3j,
            TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return new ReputationToken(contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    public static ReputationToken load(String contractAddress, Web3j web3j, Credentials credentials,
            ContractGasProvider contractGasProvider) {
        return new ReputationToken(contractAddress, web3j, credentials, contractGasProvider);
    }

    public static ReputationToken load(String contractAddress, Web3j web3j,
            TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return new ReputationToken(contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public static RemoteCall<ReputationToken> deploy(Web3j web3j, Credentials credentials,
            ContractGasProvider contractGasProvider) {
        return deployRemoteCall(ReputationToken.class, web3j, credentials, contractGasProvider, getDeploymentBinary(), "");
    }

    public static RemoteCall<ReputationToken> deploy(Web3j web3j,
            TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return deployRemoteCall(ReputationToken.class, web3j, transactionManager, contractGasProvider, getDeploymentBinary(), "");
    }

    @Deprecated
    public static RemoteCall<ReputationToken> deploy(Web3j web3j, Credentials credentials,
            BigInteger gasPrice, BigInteger gasLimit) {
        return deployRemoteCall(ReputationToken.class, web3j, credentials, gasPrice, gasLimit, getDeploymentBinary(), "");
    }

    @Deprecated
    public static RemoteCall<ReputationToken> deploy(Web3j web3j,
            TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return deployRemoteCall(ReputationToken.class, web3j, transactionManager, gasPrice, gasLimit, getDeploymentBinary(), "");
    }

    private static String getDeploymentBinary() {
        if (librariesLinkedBinary != null) {
            return librariesLinkedBinary;
        } else {
            return BINARY;
        }
    }

    protected String getStaticDeployedAddress(String networkId) {
        return _addresses.get(networkId);
    }

    public static String getPreviouslyDeployedAddress(String networkId) {
        return _addresses.get(networkId);
    }

    public static class ApprovalEventResponse extends BaseEventResponse {
        public String owner;

        public String spender;

        public BigInteger value;
    }

    public static class OwnershipTransferredEventResponse extends BaseEventResponse {
        public String previousOwner;

        public String newOwner;
    }

    public static class TransferEventResponse extends BaseEventResponse {
        public String from;

        public String to;

        public BigInteger value;
    }

    public static class UserRatedEventResponse extends BaseEventResponse {
        public String user;

        public Boolean isPositive;
    }
}
